package com.xception.messaging.features.channels.items

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.sendbird.android.User
import com.xception.messaging.R

@EpoxyModelClass(layout = R.layout.participants_item)
abstract class ParticipantModel : EpoxyModel<View>() {

    @EpoxyAttribute
    lateinit var participant: User

    override fun bind(view: View?) {
        super.bind(view)
        val nameTextView: TextView = view!!.findViewById(R.id.participants_text_view)
        nameTextView.text = participant.userId
    }
}