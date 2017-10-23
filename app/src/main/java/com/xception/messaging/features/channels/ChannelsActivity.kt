package com.xception.messaging.features.channels

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import com.sendbird.android.OpenChannel
import com.xception.messaging.R
import com.xception.messaging.features.channels.fragments.ChannelListFragment
import com.xception.messaging.features.channels.fragments.ConversationFragment
import com.xception.messaging.features.commons.BaseActivity

class ChannelsActivity: BaseActivity(), ChannelListFragment.Listener {

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

    // region ChannelListFragment.Listener

    override fun showConversation(channel: OpenChannel) {
        Log.d("test", "showConversation")
        val conversationFragment = ConversationFragment.newInstance(channel.url)
        supportFragmentManager.beginTransaction()
                .add(R.id.activity_fragment_main_container, conversationFragment)
                .addToBackStack(null)
                .commit()
    }

    // endregion

    companion object {
        fun newIntent(parentActivity: Activity) = Intent(parentActivity, ChannelsActivity::class.java)
    }
}
