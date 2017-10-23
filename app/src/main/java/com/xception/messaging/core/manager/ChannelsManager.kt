package com.xception.messaging.core.manager

import com.sendbird.android.OpenChannel
import io.reactivex.Completable
import io.reactivex.Single

object ChannelsManager {

    // Sendbird set a limit of 10 channel entered
    private val LIMIT_CHANNEL_ENTERED = 10

    // List of already entered channel
    private val mChannelEntered = ArrayList<OpenChannel>(LIMIT_CHANNEL_ENTERED)

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
        return Single.create<List<OpenChannel>>({ subscriber ->
            val channelListQuery = OpenChannel.createOpenChannelListQuery()
            channelListQuery.next({ channels, error ->
                if (error != null) {
                    subscriber.onError(error)
                } else {
                    subscriber.onSuccess(channels)
                }
            })
        })
    }

    /**
     * Enter in an open channel if not already entered
     * @param channelUrl the channel url
     */
    fun enterChannel(channel: OpenChannel): Completable {
        // Already entered in the channel, no need to request it again
        mChannelEntered.forEach {
            if (it == channel) {
                return Completable.complete()
            }
        }

        // Exit old channel if we are at the limit
        val manageLimitChannelObs: Completable
        if (mChannelEntered.size == LIMIT_CHANNEL_ENTERED) {
            manageLimitChannelObs = Completable.create({ subscriber ->
                channel.exit({ e ->
                    if (e != null) {
                        subscriber.onError(e)
                    } else {
                        mChannelEntered.removeAt(0)
                        subscriber.onComplete()
                    }
                })
            })
        } else {
            manageLimitChannelObs = Completable.complete()
        }

        // Request to enter in the channel
        return manageLimitChannelObs.andThen(
            Completable.create({ subscriber ->
                channel.enter({ e ->
                    if (e != null) {
                        subscriber.onError(e)
                    } else {
                        mChannelEntered.add(channel)
                        subscriber.onComplete()
                    }
                })
            })
        )
    }
}