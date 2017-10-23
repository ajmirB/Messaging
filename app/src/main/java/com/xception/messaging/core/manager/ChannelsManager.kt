package com.xception.messaging.core.manager

import com.sendbird.android.OpenChannel
import io.reactivex.Single

class ChannelsManager {

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
}