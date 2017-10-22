package com.xception.messaging.features.channels.items

import com.airbnb.epoxy.paging.PagingEpoxyController
import com.xception.messaging.core.model.BaseMessage
import com.xception.messaging.core.model.MessageMe
import com.xception.messaging.core.model.MessageOther

class ConversationController: PagingEpoxyController<BaseMessage>() {

    override fun buildModels(messages: MutableList<BaseMessage>) {
        messages.forEach {
            if (it is MessageMe) {
                MessageMeModel_()
                        .id("message_me" + it.message)
                        .message(it)
                        .addTo(this)
            } else if (it is MessageOther) {
                MessageOtherModel_()
                        .id("message_other" + it.message)
                        .message(it)
                        .addTo(this)
            }
        }
    }
}