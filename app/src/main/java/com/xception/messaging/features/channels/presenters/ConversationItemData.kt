package com.xception.messaging.features.channels.presenters

abstract class MessageItemData()

data class MessageMeItemData(val messageId: Long, val message: String): MessageItemData()

data class MessageOtherItemData(val messageId: Long, val message: String, val senderName: String): MessageItemData()