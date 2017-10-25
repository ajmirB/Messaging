package com.xception.messaging.features.commons.presenter

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

    open fun onViewResumed() {}

    fun onViewDestroyed() {
        mCompositeDisposable.dispose()
    }

    interface View
}
