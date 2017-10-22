package com.xception.messaging.features.signin

import com.xception.messaging.features.commons.BasePresenter

interface SignInView : BasePresenter.View {
    fun goToGeneralChannel()
}