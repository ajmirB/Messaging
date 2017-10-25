package com.xception.messaging.features.channels.presenters

import com.sendbird.android.User
import com.xception.messaging.core.manager.ChannelManager
import com.xception.messaging.core.manager.ChannelsManager
import com.xception.messaging.core.store.UserStore
import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.helper.toItemData
import com.xception.messaging.helper.toListItemData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ConversationPresenter(mView: ConversationView, private val channelUrl: String): BasePresenter<ConversationView>(mView) {

    private var CHANNEL_HANDLER_ID = "CHANNEL_INCOMING_MESSAGE_LISTENER"

    private lateinit var mConnectedUser: User

    private lateinit var mChannelManager: ChannelManager

    // Data fetch
    var mMessages = ArrayList<MessageItemData>(100)

    override fun onViewCreated() {
        super.onViewCreated()

        val userStore = UserStore()
        // In conversation, the user is always connected
        mConnectedUser = userStore.getUser()!!

        // Initial messages
        val disposable = ChannelsManager.getOpenChannel(channelUrl)
                .subscribeOn(Schedulers.io())
                .doOnSuccess({
                    mChannelManager = ChannelManager(it)
                    onIncomingMessageListen()
                })
                .flatMapMaybe({ mChannelManager.getPreviousMessage() })
                .map({ it.toListItemData(mConnectedUser) })
                .doOnSuccess { messages -> mMessages.addAll(messages) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mView.initContent(mMessages) },
                        { throwable -> throwable.printStackTrace() }
                )
        mCompositeDisposable.add(disposable)
    }

    fun onParticipantsListClick() {
        mView.goToParticipants(mChannelManager.channel)
    }

    fun onSendButtonClicked(sendMessage: String) {
        if (!sendMessage.isEmpty()) {
            val disposable = mChannelManager.sendMessage(sendMessage)
                    .subscribeOn(Schedulers.io())
                    .map({ it.toItemData(mConnectedUser) })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { message ->
                                mMessages.add(0, message)
                                mView.addIncomingMessage(mMessages)
                                mView.resetInputText()
                            },
                            { throwable -> throwable.printStackTrace() }
                    )
            mCompositeDisposable.add(disposable)
        }
    }

    fun onLoadingMore() {
        val disposable = mChannelManager.getPreviousMessage()
                .subscribeOn(Schedulers.io())
                .map({ it.toListItemData(mConnectedUser) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { messages ->
                            mMessages.addAll(messages)
                            mView.addPreviousMessages(mMessages)
                        },
                        { throwable -> throwable.printStackTrace() },
                        { mView.stopPaginate() }
                )
        mCompositeDisposable.add(disposable)
    }

    private fun onIncomingMessageListen() {
        val incomingMessagedisposable = mChannelManager.onMessageIncoming(CHANNEL_HANDLER_ID)
                .subscribeOn(Schedulers.io())
                .map { it.toItemData(mConnectedUser) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { message ->
                            mMessages.add(0, message)
                            mView.addIncomingMessage(mMessages)
                        },
                        { throwable -> throwable.printStackTrace() }
                )
        mCompositeDisposable.add(incomingMessagedisposable)
    }
}