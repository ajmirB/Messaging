package com.xception.messaging.features.channels.presenters

import android.arch.paging.DataSource
import com.xception.messaging.core.model.BaseMessage
import com.xception.messaging.features.commons.presenter.BasePresenter

interface ConversationView: BasePresenter.View {
    fun showContent(dataSource: DataSource<Int, BaseMessage>)
}