package com.xception.messaging.features.channels.presenters

import com.sendbird.android.BaseMessage
import com.xception.messaging.features.commons.presenter.BasePresenter

interface ConversationView: BasePresenter.View {
    fun initContent(messages: List<BaseMessage>)
    fun showContent(messages: List<BaseMessage>)
    fun stopPaginate()
    fun resetInputText()
}