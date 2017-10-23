package com.xception.messaging.features.channels

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xception.messaging.R
import com.xception.messaging.features.channels.fragments.ChannelListFragment
import com.xception.messaging.features.channels.fragments.ConversationFragment

class ChannelsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        showChannelList()
    }

    fun showChannelList(){
        val channelListFragment = ChannelListFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_main_container, channelListFragment)
                .commit()
    }

    fun showGeneralChannel(){
        val conversationFragment = ConversationFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_main_container, conversationFragment)
                .commit()
    }

    companion object {
        fun newIntent(parentActivity: Activity) = Intent(parentActivity, ChannelsActivity::class.java)
    }
}
