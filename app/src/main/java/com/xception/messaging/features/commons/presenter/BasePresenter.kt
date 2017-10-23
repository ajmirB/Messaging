package com.xception.messaging.features.commons.presenter

import android.util.Log
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T: BasePresenter.View>(protected var mView: T) {

    protected var mCompositeDisposable = CompositeDisposable()
        get() {
            if (field.isDisposed) {
                field = CompositeDisposable()
            }
            return field
        }

    open fun onViewCreated() {}

    fun onViewDestroyed() {
        mCompositeDisposable.dispose()
    }

    interface View
}
