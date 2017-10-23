package com.xception.messaging.helper

import com.sendbird.android.BaseChannel
import com.xception.messaging.features.channels.presenters.ChannelItemData

fun BaseChannel.convertToItemData(): ChannelItemData = ChannelItemData(name, url, null)