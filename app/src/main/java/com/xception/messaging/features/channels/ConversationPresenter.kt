package com.xception.messaging.features.channels

import android.arch.paging.KeyedDataSource
import com.xception.messaging.core.model.BaseMessage
import com.xception.messaging.core.model.MessageMe
import com.xception.messaging.core.model.MessageOther
import com.xception.messaging.features.commons.BasePresenter
import java.util.*

class ConversationPresenter(mView: ConversationView): BasePresenter<ConversationView>(mView) {

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

        // Dummy data for now
        mMessages = ArrayList<BaseMessage>(100)
        for (i in 1..100) {
            if (i % 3 == 0)
                mMessages.add(MessageMe(i.toString()))
            else
                mMessages.add(MessageOther(i.toString()))
        }

        // Display the datasource in the view
        mView.showContent(mDataSource)
    }
}