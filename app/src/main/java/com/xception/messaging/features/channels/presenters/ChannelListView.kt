package com.xception.messaging.features.channels.presenters

import com.sendbird.android.OpenChannel
import com.xception.messaging.features.commons.presenter.BasePresenter

interface ChannelListView : BasePresenter.View {
    fun showContent(channels: List<ChannelItemData>)
    fun updateContent(channels: List<ChannelItemData>)
    fun showChannelCreationForm()
    fun goToConversation(channel: OpenChannel)
}