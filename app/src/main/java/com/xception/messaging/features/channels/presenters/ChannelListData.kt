package com.xception.messaging.features.channels.presenters

import android.view.View

data class ChannelItemData(val name: String, val url: String, var onClickListener: View.OnClickListener?)