package com.xception.messaging.features.signin

import com.xception.messaging.features.commons.presenter.AlertCommonErrorView
import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.features.commons.presenter.LoadingView

interface SignInView: BasePresenter.View, LoadingView, AlertCommonErrorView {
    fun prefillNickname(nickname: String)
    fun goToGeneralChannel()
}