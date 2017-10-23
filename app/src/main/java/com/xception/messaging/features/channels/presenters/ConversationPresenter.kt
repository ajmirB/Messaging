package com.xception.messaging.features.channels.presenters

import com.sendbird.android.BaseMessage
import com.xception.messaging.core.manager.ChannelManager
import com.xception.messaging.core.manager.ChannelsManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConversationPresenter(mView: ConversationView, val channelUrl: String): BasePresenter<ConversationView>(mView) {

    private lateinit var mChannelManager: ChannelManager

    // Data fetch
    var mMessages = ArrayList<BaseMessage>(100)

    override fun onViewCreated() {
        super.onViewCreated()

            }
        }
        ChannelsManager.getOpenChannel(channelUrl)
                .subscribeOn(Schedulers.io())
                .doOnSuccess({ mChannelManager = ChannelManager(it) })
                .flatMapMaybe({ mChannelManager.getPreviousMessage() })
                .doOnSuccess { messages -> mMessages.addAll(messages) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mView.initContent(mMessages) },
                        { throwable -> throwable.printStackTrace() },
                        { mView.stopPaginate() }
                )
    }

    fun onSendButtonClicked(message: String) {
        if (!message.isEmpty()) {
            mChannelManager.sendMessage(message)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { mView.resetInputText() },
                            { throwable -> throwable.printStackTrace() }
                    )
        }
    }

    fun onLoadingMore() {
        mChannelManager.getPreviousMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { messages ->
                            mMessages.addAll(messages)
                            mView.showContent(mMessages)
                        },
                        { throwable -> throwable.printStackTrace() },
                        { mView.stopPaginate() }
                )
    }
}