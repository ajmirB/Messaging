package com.xception.messaging.features.channels.presenters

import com.sendbird.android.BaseMessage
import com.xception.messaging.core.manager.ChannelManager
import com.xception.messaging.core.manager.ChannelsManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ConversationPresenter(mView: ConversationView, private val channelUrl: String): BasePresenter<ConversationView>(mView) {

    private var CHANNEL_HANDLER_ID = "CHANNEL_INCOMING_MESSAGE_LISTENER"

    private lateinit var mChannelManager: ChannelManager

    // Data fetch
    var mMessages = ArrayList<BaseMessage>(100)

    override fun onViewCreated() {
        super.onViewCreated()

        // Initial messages
        val disposable = ChannelsManager.getOpenChannel(channelUrl)
                .subscribeOn(Schedulers.io())
                .doOnSuccess({
                    mChannelManager = ChannelManager(it)
                    onIncomingMessageListen()
                })
                .flatMapMaybe({ mChannelManager.getPreviousMessage() })
                .doOnSuccess { messages -> mMessages.addAll(messages) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mView.initContent(mMessages) },
                        { throwable -> throwable.printStackTrace() }
                )
        mCompositeDisposable.add(disposable)
    }

    fun onSendButtonClicked(message: String) {
        if (!message.isEmpty()) {
            val disposable = mChannelManager.sendMessage(message)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { message ->
                                mView.resetInputText()
                                mMessages.add(0, message)
                                mView.addPreviousMessages(mMessages)
                            },
                            { throwable -> throwable.printStackTrace() }
                    )
            mCompositeDisposable.add(disposable)
        }
    }

    fun onLoadingMore() {
        val disposable = mChannelManager.getPreviousMessage()
                .subscribeOn(Schedulers.io())
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { message ->
                            mMessages.add(0, message)
                            mView.addPreviousMessages(mMessages)
                        },
                        { throwable -> throwable.printStackTrace() }
                )
        mCompositeDisposable.add(incomingMessagedisposable)
    }
}