package com.xception.messaging.features.channels.presenters

import android.view.View
import com.sendbird.android.BaseChannel
import com.xception.messaging.core.manager.ChannelsManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.helper.convertToItemData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ChannelListPresenter(mView: ChannelListView): BasePresenter<ChannelListView>(mView) {

    val mChannelsManager = ChannelsManager()

    override fun onViewCreated() {
        super.onViewCreated()

        // Fetch channels
        val disposable = mChannelsManager.getOpenChannels()
                .flatMapObservable { Observable.fromIterable(it) }
                .map({ channel ->
                    val itemData = channel.convertToItemData()
                    itemData.onClickListener = android.view.View.OnClickListener { onChannelItemClicked(channel) }
                    return@map itemData
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { channels -> mView.showContent(channels) },
                        { throwable -> throwable.printStackTrace() }
                )
        mCompositeDisposable.add(disposable)
    }

    private fun onChannelItemClicked(channel: BaseChannel) {
        //TODO: Open the conversation link to the channel
    }
}