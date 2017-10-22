package com.xception.messaging.features.signin

import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.features.commons.presenter.LoadingView

interface SignInView: BasePresenter.View, LoadingView {
    fun goToGeneralChannel()
}