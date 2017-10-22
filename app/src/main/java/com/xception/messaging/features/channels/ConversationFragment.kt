package com.xception.messaging.features.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.xception.messaging.R
import com.xception.messaging.core.model.BaseMessage
import com.xception.messaging.core.model.MessageMe
import com.xception.messaging.core.model.MessageOther
import com.xception.messaging.features.channels.items.MessageMeModel_
import com.xception.messaging.features.channels.items.MessageOtherModel_
import com.xception.messaging.features.commons.BaseFragment

class ConversationFragment: BaseFragment(), ConversationView {

    lateinit var mConversationPresenter: ConversationPresenter

    lateinit var mRecyclerView: EpoxyRecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_conversation, container, false)

        mConversationPresenter = ConversationPresenter(this)

        mRecyclerView = view.findViewById(R.id.conversation_epoxy_recycler_view)
        mRecyclerView.buildModelsWith { controller ->
            val messages = ArrayList<BaseMessage>(100)
            for (i in 1..100) {
                if (i % 3 == 0)
                    messages.add(MessageMe(i.toString()))
                else
                    messages.add(MessageOther(i.toString()))
            }

            messages.forEach {
                if (it is MessageMe) {
                    MessageMeModel_()
                            .id("message_me" + it.message)
                            .message(it)
                            .addTo(controller)
                } else if (it is MessageOther) {
                    MessageOtherModel_()
                            .id("message_other" + it.message)
                            .message(it)
                            .addTo(controller)
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mConversationPresenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mConversationPresenter.onViewDestroyed()
    }

    companion object {
        fun newInstance() = ConversationFragment()
    }
}