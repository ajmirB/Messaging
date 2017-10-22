package com.xception.messaging.features

import android.app.Application
import com.sendbird.android.SendBird
import com.xception.messaging.BuildConfig

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        SendBird.init(BuildConfig.SENDBIRD_APPLICATION_ID, this)
    }
}