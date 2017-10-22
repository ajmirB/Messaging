package com.xception.messaging.features.signin

import com.xception.messaging.features.commons.BasePresenter

class SignInPresenter(mView: SignInView) : BasePresenter<SignInView>(mView) {

    fun onLoginClicked(nickname: String)  {
        mView.goToGeneralChannel()
    }
}
