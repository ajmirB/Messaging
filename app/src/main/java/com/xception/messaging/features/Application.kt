package com.xception.messaging.features

import android.app.Application
import com.sendbird.android.SendBird
import com.xception.messaging.BuildConfig
import com.xception.messaging.helper.ApplicationHelper

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationHelper.context = applicationContext
        SendBird.init(BuildConfig.SENDBIRD_APPLICATION_ID, applicationContext)
    }
}