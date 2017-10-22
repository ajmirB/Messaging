package com.xception.messaging.features.signin

import com.xception.messaging.core.manager.UserManager
import com.xception.messaging.features.commons.presenter.BasePresenter
import com.xception.messaging.helper.DeviceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInPresenter(mView: SignInView): BasePresenter<SignInView>(mView) {

    private val mUserManager = UserManager()

    override fun onViewCreated() {
        super.onViewCreated()

        // Observe on maybe an already connected user
        mUserManager.getConnectedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            // Auto sign in with the stored user information
                            mView.prefillNickname(user.userId)
                            onLoginClicked(user.userId)
                        },
                        { throwable -> throwable.printStackTrace() }
                )
    }

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
