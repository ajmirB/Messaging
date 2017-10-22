package com.xception.messaging.features.signin

import com.xception.messaging.core.manager.UserManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.helper.DeviceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInPresenter(mView: SignInView): BasePresenter<SignInView>(mView) {

    val mUserManager = UserManager()

    fun onLoginClicked(nickname: String)  {
        if (!DeviceHelper.isNetworkAvailable()){
            mView.alertNetworkError()
            return
        }

        mView.showLoadingView()
        mUserManager.signIn(nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate({ mView.hideLoadingView() })
                .subscribe(
                        { user -> mView.goToGeneralChannel()},
                        { throwable ->
                            mView.alertUnknownError()
                            throwable.printStackTrace()
                        }
                )
    }
}
