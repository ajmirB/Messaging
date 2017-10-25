package com.xception.messaging.helper

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.sendbird.android.BaseMessage
import com.sendbird.android.User
import com.sendbird.android.UserMessage
import com.xception.messaging.features.channels.items.MessageMeModel_
import com.xception.messaging.features.channels.items.MessageOtherModel_
import com.xception.messaging.features.channels.presenters.MessageItemData
import com.xception.messaging.features.channels.presenters.MessageMeItemData
import com.xception.messaging.features.channels.presenters.MessageOtherItemData
import java.util.*

fun UserMessage.toItemData(connectedUser: User): MessageItemData {
    return if (sender.userId == connectedUser.userId) {
        // Message from the connected User
        MessageMeItemData(this.messageId, message)
    } else {
        // Message from other
        MessageOtherItemData(messageId, message, sender.userId, sender.profileUrl)
    }
}

fun List<BaseMessage>.toListItemData(connectedUser: User): List<MessageItemData> {
    val messageItemsData = ArrayList<MessageItemData>(size)
    forEach {
        if (it is UserMessage) {
            messageItemsData.add(it.toItemData(connectedUser))
        }
    }
    return messageItemsData
}

fun List<MessageItemData>.toModel(): List<EpoxyModel<View>> {
    val messageModel = ArrayList<EpoxyModel<View>>(size)
    forEach {
        if (it is MessageMeItemData) {
            messageModel.add(
                    MessageMeModel_()
                            .id(it.messageId)
                            .message(it)
            )
        } else if (it is MessageOtherItemData){
            messageModel.add(
                    MessageOtherModel_()
                            .id(it.messageId)
                            .message(it)
            )
        }
    }
    return messageModel
}