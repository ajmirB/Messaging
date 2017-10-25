package com.xception.messaging.features.channels.items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

        val mIconImageView: ImageView = view.findViewById(R.id.channel_list_channel_icon_image_view)
        Glide.with(view.context).load(channel.imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(mIconImageView)

        view.setOnClickListener(channel.onClickListener)
    }
}