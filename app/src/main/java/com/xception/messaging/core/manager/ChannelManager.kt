package com.xception.messaging.core.manager

import com.sendbird.android.*
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single


class ChannelManager(val channel: BaseChannel) {

    private var mPreviousMessageListQuery: PreviousMessageListQuery? = null

    // All message are loaded when we get less messages than we requested
    private var mAllMessageAreLoaded = false

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
        if (mAllMessageAreLoaded) {
            // No more message to loaded
            return Maybe.empty()
        } else {
            return Maybe.create<List<BaseMessage>> { subscriber ->
                if (mPreviousMessageListQuery == null) {
                    // Store only one query to load all the message link to the channel
                    mPreviousMessageListQuery = channel.createPreviousMessageListQuery()
                }

                // Launch the request to get previous message
                mPreviousMessageListQuery!!.load(LIMIT_PREVIOUS_MESSAGE_PAGED, true, { messages, e ->
                    when {
                        e != null -> { subscriber.onError(e) }
                        messages.isEmpty() -> { subscriber.onComplete() }
                        else -> {
                            mAllMessageAreLoaded = messages.size < LIMIT_PREVIOUS_MESSAGE_PAGED
                            subscriber.onSuccess(messages)
                        }
                    }
                })
            }
        }
    }

    /**
     * Listener on new incoming message
     */
    fun onMessageIncoming(channelHanderId: String): Observable<BaseMessage> {
        return Observable.create { subscriber ->
            SendBird.addChannelHandler(channelHanderId, object:SendBird.ChannelHandler() {
                override fun onMessageReceived(baseChannel: BaseChannel, baseMessage: BaseMessage) {
                    // Add new message to view
                    if (baseChannel.url == channel.url) {
                        subscriber.onNext(baseMessage)
                    }
                }
            })
        }
    }

    /**
     * Send a message to the current channel
     * @param message the message to add in the channel
     */
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
        val LIMIT_PREVIOUS_MESSAGE_PAGED = 5
    }
}