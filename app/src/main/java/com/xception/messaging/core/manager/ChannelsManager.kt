package com.xception.messaging.core.manager

import com.sendbird.android.OpenChannel
import io.reactivex.Completable
import io.reactivex.Single

object ChannelsManager {

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
     * Get all open channels
     */
    fun getOpenChannels(): Single<List<OpenChannel>> {
        return Single.create<List<OpenChannel>> { subscriber ->
            val channelListQuery = OpenChannel.createOpenChannelListQuery()
            channelListQuery.next({ channels, error ->
                if (error != null) {
                    subscriber.onError(error)
                } else {
                    subscriber.onSuccess(channels)
                }
            })
        }
    }

    /**
     * Enter in an open channel if not already entered
     * @param channelUrl the channel url
     */
    fun enterChannel(channel: OpenChannel): Completable {
        // Request to enter in the channel
        return Completable.create { subscriber ->
                channel.enter({ e ->
                    if (e != null) {
                        subscriber.onError(e)
                    } else {
                        subscriber.onComplete()
                    }
                })
            }
    }

    fun createOpenChannel(name: String): Single<OpenChannel> {
        return Single.create { subcriber ->
            OpenChannel.createChannel(name, null, null, null, { openChannel, e ->
                if (e != null) {
                    subcriber.onError(e)
                } else {
                    subcriber.onSuccess(openChannel)
                }
            })
        }
    }
}