package com.xception.messaging.features.channels

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.xception.messaging.R

class ChannelsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    companion object {
        fun newIntent(parentActivity: Activity) = Intent(parentActivity, ChannelsActivity::class.java)
    }
}
