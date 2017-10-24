package com.xception.messaging.features.channels.presenters

import com.sendbird.android.User
import com.xception.messaging.features.commons.presenter.BasePresenter

interface ParticipantsView : BasePresenter.View {
    fun showParticipants(participants: List<User>)
}