package com.xception.messaging.features.commons

import io.reactivex.disposables.CompositeDisposable

class BasePresenter<T : View>(protected var mView: T) {

    protected var mCompositeDisposable: CompositeDisposable

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    fun onViewCreated() {}

    fun onViewDestroyed() {
        mCompositeDisposable.dispose()
    }
}

interface View
