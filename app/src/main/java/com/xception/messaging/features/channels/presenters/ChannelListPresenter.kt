package com.xception.messaging.features.channels.presenters

import android.view.View
import com.sendbird.android.BaseChannel
import com.sendbird.android.OpenChannel
import com.xception.messaging.core.manager.ChannelsManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.helper.convertToItemData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChannelListPresenter(mView: ChannelListView): BasePresenter<ChannelListView>(mView) {

    override fun onViewCreated() {
        super.onViewCreated()

        // Fetch channels
        val disposable = ChannelsManager.getOpenChannels()
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
        if (channel is OpenChannel) {
            ChannelsManager.enterChannel(channel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { mView.goToConversation(channel) },
                            { throwable -> throwable.printStackTrace() }
                    )
        }
    }
}