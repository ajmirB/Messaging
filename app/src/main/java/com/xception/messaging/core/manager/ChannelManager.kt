package com.xception.messaging.core.manager

import android.util.Log
import com.sendbird.android.*
import io.reactivex.Maybe
import io.reactivex.Single


class ChannelManager(val channel: BaseChannel) {

    private var mPreviousMessageListQuery: PreviousMessageListQuery? = null

    /**
     * Get channel identified by its url
     * @parem channelUrl the url of the channel
     */
    fun getOpenChannel(channelUrl: String): Single<OpenChannel> {
        return Single.create<OpenChannel> { subscriber ->
            OpenChannel.getChannel(channelUrl, { channel, e ->
                if (e != null) {
                    subscriber.onError(e)
                } else {
                    subscriber.onSuccess(channel)
                }
            })
        }
    }

    /**
     * Get previous message for the channel identified in the constructor.
     * The number of message are limited by 20
     */
    fun getPreviousMessage(): Maybe<List<BaseMessage>> {
        return Maybe.create<List<BaseMessage>> { subscriber ->
            if (mPreviousMessageListQuery == null) {
                // Store only one query to load all the message link to the channel
                mPreviousMessageListQuery = channel.createPreviousMessageListQuery()
            }

            // Launch the request to get previous message
            mPreviousMessageListQuery!!.load(LIMIT_PREVIOUS_MESSAGE_PAGED, false, { messages, e ->
                if (e != null) {
                    Log.d("test", "error" + e)
                    subscriber.onError(e)
                } else if (messages.isEmpty()) {
                    Log.d("test", "empty")
                    subscriber.onComplete()
                } else {
                    Log.d("test", "messages: " + messages.size)
                    subscriber.onSuccess(messages)
                }
            })
        }
    }

    fun sendMessage(message: String): Single<UserMessage> {
        return Single.create { subscriber ->
            channel.sendUserMessage(message, {userMessage, e ->
                if (e != null) {
                    subscriber.onError(e)
                } else {
                    subscriber.onSuccess(userMessage)
                }
            })
        }
    }

    companion object {
        // Past messages are queried in fixed numbers
        val LIMIT_PREVIOUS_MESSAGE_PAGED = 10
    }
}