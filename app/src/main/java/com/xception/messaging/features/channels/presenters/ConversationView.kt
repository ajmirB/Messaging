package com.xception.messaging.features.channels.presenters

import com.sendbird.android.BaseChannel
import com.xception.messaging.features.commons.presenter.BasePresenter

interface ConversationView: BasePresenter.View {

    /**
     * Init the list with a list of model
     */
    fun initContent(messages: List<MessageItemData>)

    /**
     * Update all the list of messages with a new one which had previous messages
     */
    fun addPreviousMessages(messages: List<MessageItemData>)

    /**
     * Update all the lis of messages with a new one which had incoming messages
     */
    fun addIncomingMessage(messages: List<MessageItemData>)

    fun stopPaginate()
    fun resetInputText()
    fun goToParticipants(channel: BaseChannel)
}