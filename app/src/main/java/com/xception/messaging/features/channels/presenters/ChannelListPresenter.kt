package com.xception.messaging.features.channels.presenters

import com.sendbird.android.BaseChannel
import com.sendbird.android.OpenChannel
import com.xception.messaging.core.manager.ChannelsManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.helper.convertToItemData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChannelListPresenter(mView: ChannelListView): BasePresenter<ChannelListView>(mView) {

    private var mChannelItemsData = ArrayList<ChannelItemData>(10).toMutableList()

    override fun onViewResumed() {
        super.onViewResumed()

        // Fetch channel list from sendbird backend
        val disposable = ChannelsManager.getOpenChannels()
                .subscribeOn(Schedulers.io())
                .flatMapObservable { Observable.fromIterable(it) }
                .map({ channel ->
                    val itemData = channel.convertToItemData()
                    itemData.onClickListener = android.view.View.OnClickListener { onChannelItemClicked(channel) }
                    return@map itemData
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { channels ->
                            mChannelItemsData = channels
                            mView.showContent(mChannelItemsData)
                        },
                        { throwable -> throwable.printStackTrace() }
                )
        mCompositeDisposable.add(disposable)
    }

    fun onAskToCreateChannelClicked() {
        mView.showChannelCreationForm()
    }

    fun onCreateChannelConfirmationClicked(channelName: String) {
        val disposable = ChannelsManager.createOpenChannel(channelName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { channel ->
                    val itemData = channel.convertToItemData()
                    itemData.onClickListener = android.view.View.OnClickListener { onChannelItemClicked(channel) }
                    mChannelItemsData.add(0, itemData)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { channel -> mView.showContent(mChannelItemsData) },
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