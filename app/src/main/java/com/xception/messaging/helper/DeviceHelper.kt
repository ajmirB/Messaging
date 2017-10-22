package com.xception.messaging.helper

import android.content.Context
import android.net.ConnectivityManager


class DeviceHelper {

    companion object {

        fun isNetworkAvailable(): Boolean {
            val context = ApplicationHelper.context
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}