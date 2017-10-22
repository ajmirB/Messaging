package com.xception.messaging.features.commons

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

object UiThreadExecutor : Executor {
    private val mHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mHandler.post(command)
    }
}
