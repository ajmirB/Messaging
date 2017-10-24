package com.xception.messaging.features.channels.presenters

import com.xception.messaging.core.manager.ChannelManager
import com.xception.messaging.core.manager.ChannelsManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ParticipantsPresenter(mView: ParticipantsView, private val channelUrl: String): BasePresenter<ParticipantsView>(mView) {

    private lateinit var mChannelManager: ChannelManager

    override fun onViewCreated() {
        super.onViewCreated()

        // Get participants
        val disposable = ChannelsManager.getOpenChannel(channelUrl)
                .subscribeOn(Schedulers.io())
                .doOnSuccess({ mChannelManager = ChannelManager(it) })
                .flatMapMaybe({ mChannelManager.getParticipantsList() })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { participants -> mView.showParticipants(participants) },
                        { throwable -> throwable.printStackTrace() }
                )
        mCompositeDisposable.add(disposable)
    }
}