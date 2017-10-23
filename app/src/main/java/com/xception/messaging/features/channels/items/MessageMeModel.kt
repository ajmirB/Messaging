package com.xception.messaging.features.channels.items

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.sendbird.android.UserMessage
import com.xception.messaging.R

@EpoxyModelClass(layout = R.layout.conversation_message_me_item)
abstract class MessageMeModel : EpoxyModel<View>() {

    @EpoxyAttribute
    lateinit var message: UserMessage

    override fun bind(view: View?) {
        super.bind(view)
        val textView: TextView = view!!.findViewById(R.id.message_me_text_view)
        textView.text = message.message
    }
}