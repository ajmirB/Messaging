package com.xception.messaging.features.channels.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sendbird.android.User
import com.xception.messaging.R
import com.xception.messaging.features.channels.fragments.ConversationFragment.Companion.CHANNEL_URL_KEY
import com.xception.messaging.features.channels.items.ParticipantModel_
import com.xception.messaging.features.channels.presenters.ParticipantsPresenter
import com.xception.messaging.features.channels.presenters.ParticipantsView
import com.xception.messaging.features.commons.BaseFragment

class ParticipantsFragment : BaseFragment(), ParticipantsView {

    lateinit var mParticipantsPresenter: ParticipantsPresenter

    lateinit var mRecyclerView: EpoxyRecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_participants, container, false)

        val channelUrl = arguments.getString(CHANNEL_URL_KEY)

        mParticipantsPresenter = ParticipantsPresenter(this, channelUrl!!)

        mRecyclerView = view.findViewById(R.id.participants_epoxy_recycler_view)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mParticipantsPresenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mParticipantsPresenter.onViewDestroyed()
    }

    // region ParticipantsView

    override fun showParticipants(participants: List<User>) {
        mRecyclerView.buildModelsWith { controller ->
            participants.forEach {
                ParticipantModel_()
                        .id(it.userId)
                        .participant(it)
                        .addTo(controller)
            }
        }
    }

    // endregion

    companion object {

        fun newInstance(channelUrl: String): ParticipantsFragment {
            val bundle = Bundle()
            bundle.putString(CHANNEL_URL_KEY, channelUrl)

            val fragment = ParticipantsFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}