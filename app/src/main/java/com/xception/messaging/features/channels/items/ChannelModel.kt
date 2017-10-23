package com.xception.messaging.features.channels.items

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.xception.messaging.R
import com.xception.messaging.features.channels.presenters.ChannelItemData

@EpoxyModelClass(layout = R.layout.channel_list_channel_item)
abstract class ChannelModel : EpoxyModel<View>() {

    @EpoxyAttribute
    lateinit var channel: ChannelItemData

    override fun bind(view: View?) {
        super.bind(view)
        val nameTextView: TextView = view!!.findViewById(R.id.channel_list_channel_name_text_view)
        nameTextView.text = channel.name

        val urlTextView: TextView = view.findViewById(R.id.channel_list_channel_url_text_view)
        urlTextView.text = channel.url

        view.setOnClickListener(channel.onClickListener)
    }
}