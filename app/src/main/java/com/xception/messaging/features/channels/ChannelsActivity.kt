package com.xception.messaging.features.channels

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.xception.messaging.R
import com.xception.messaging.features.channels.fragments.ChannelListFragment
import com.xception.messaging.features.channels.fragments.ConversationFragment
import com.xception.messaging.features.commons.BaseActivity

class ChannelsActivity: BaseActivity() {

    private lateinit var mTopToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)

        mTopToolbar = findViewById(R.id.toolbar)
        setToolbarProperties(mTopToolbar)

        showChannelList()
    }

    fun showChannelList(){
        val channelListFragment = ChannelListFragment.newInstance()
        title = getString(R.string.channel_list_title)
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
