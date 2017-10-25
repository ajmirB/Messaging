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
import com.xception.messaging.features.channels.presenters.MessageOtherItemData

@EpoxyModelClass(layout = R.layout.conversation_message_other_item)
abstract class MessageOtherModel : EpoxyModel<View>() {

    @EpoxyAttribute
    lateinit var message: MessageOtherItemData

    override fun bind(view: View?) {
        super.bind(view)

        val senderTextView: TextView = view!!.findViewById(R.id.message_other_sender_name_text_view)
        senderTextView.text = view.context.getString(R.string.conversation_sender_format_text, message.senderName)

        val messageTextView: TextView = view.findViewById(R.id.message_other_text_view)
        messageTextView.text = message.message

        val mSenderImageView: ImageView = view.findViewById(R.id.conversation_message_sender_image_view)
        Glide.with(view.context).load(message.senderImgUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(mSenderImageView)
    }
}