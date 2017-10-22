package com.xception.messaging.features.commons.presenter

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T: BasePresenter.View>(protected var mView: T) {

    protected var mCompositeDisposable: CompositeDisposable

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    open fun onViewCreated() {}

    fun onViewDestroyed() {
        mCompositeDisposable.dispose()
    }

    interface View
}
