package com.xception.messaging.features.channels.presenters

import com.xception.messaging.features.commons.presenter.BasePresenter

interface ChannelListView : BasePresenter.View {
    fun showContent(channels: List<ChannelItemData>)
}