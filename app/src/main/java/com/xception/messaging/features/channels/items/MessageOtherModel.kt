package com.xception.messaging.features.channels.items

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
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
    }
}