package com.xception.messaging.features.channels.presenters

import android.arch.paging.DataSource
import com.sendbird.android.BaseMessage
import com.xception.messaging.features.commons.presenter.BasePresenter

interface ConversationView: BasePresenter.View {
    fun showContent(dataSource: DataSource<Int, BaseMessage>)
}