package com.xception.messaging.features.channels.presenters

import android.arch.paging.KeyedDataSource
import com.sendbird.android.BaseMessage
import com.xception.messaging.core.manager.ChannelManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import io.reactivex.schedulers.Schedulers
import java.util.*

class ConversationPresenter(mView: ConversationView, val channelUrl: String): BasePresenter<ConversationView>(mView) {

    var mChannelManager = ChannelManager(channelUrl)

    // Data fetch
    var mMessages = Collections.emptyList<BaseMessage>()

    // Data source which manage paging (display a pageSize item at once)
    val mDataSource = object: KeyedDataSource<Int, BaseMessage>() {

        override fun getKey(item: BaseMessage): Int =
                (0 until mMessages.size).firstOrNull { mMessages[it] == item } ?: 0

        override fun loadBefore(currentBeginKey: Int, pageSize: Int): MutableList<BaseMessage> {
            return if (currentBeginKey == 0) {
                Collections.emptyList()
            } else {
                val begin = Math.max(0, currentBeginKey - pageSize)
                mMessages.subList(begin, currentBeginKey).toMutableList()
            }
        }

        override fun loadAfter(currentEndKey: Int, pageSize: Int): MutableList<BaseMessage> {
            return if (currentEndKey == mMessages.size - 1) {
                Collections.emptyList()
            } else {
                val end = Math.min(mMessages.size, currentEndKey + 1 + pageSize)
                mMessages.subList(currentEndKey + 1, end).toMutableList()
            }
        }

        override fun loadInitial(pageSize: Int): MutableList<BaseMessage> {
            val limit = if (pageSize < mMessages.size) pageSize else mMessages.size
            return mMessages.subList(0, limit).toMutableList()
        }
    }

    override fun onViewCreated() {
        super.onViewCreated()

        mChannelManager.getPreviousMessage()
                .subscribeOn(Schedulers.io())
                .subscribe()

        // Display the datasource in the view
        mView.showContent(mDataSource)
    }
}