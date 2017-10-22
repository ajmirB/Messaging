package com.xception.messaging.core.model

abstract class BaseMessage {
    abstract val message: String
}

data class MessageMe(override val message: String): BaseMessage()

data class MessageOther(override val message: String): BaseMessage()