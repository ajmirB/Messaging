package com.xception.messaging.features.channels.items

import com.airbnb.epoxy.paging.PagingEpoxyController
import com.sendbird.android.BaseMessage
import com.sendbird.android.UserMessage

class ConversationController: PagingEpoxyController<BaseMessage>() {

    override fun buildModels(messages: MutableList<BaseMessage>) {
        messages.forEach {
            if (it is UserMessage) {
                MessageMeModel_()
                        .id(it.messageId)
                        .message(it)
                        .addTo(this)
            }
        }
    }
}