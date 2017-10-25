package com.xception.messaging.features.channels.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sendbird.android.OpenChannel
import com.xception.messaging.R
import com.xception.messaging.features.channels.items.ChannelModel_
import com.xception.messaging.features.channels.presenters.ChannelItemData
import com.xception.messaging.features.channels.presenters.ChannelListPresenter
import com.xception.messaging.features.channels.presenters.ChannelListView
import com.xception.messaging.features.commons.BaseFragment

class ChannelListFragment : BaseFragment(), ChannelListView {

    lateinit var mChannelListPresenter: ChannelListPresenter

    lateinit var mRecyclerView: EpoxyRecyclerView

    lateinit var mFragmentListener: Listener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is Listener) {
            mFragmentListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_channel_list, container, false)

        mChannelListPresenter = ChannelListPresenter(this)

        mRecyclerView = view.findViewById(R.id.channel_list_epoxy_recycler_view)
        mRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mChannelListPresenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mChannelListPresenter.onViewDestroyed()
    }

    // region ChannelListView

    override fun showContent(channels: List<ChannelItemData>) {
        mRecyclerView.buildModelsWith { controller ->
            channels.forEach {
                ChannelModel_()
                        .id(it.url)
                        .channel(it)
                        .addTo(controller)
            }
        }
    }

    override fun goToConversation(channel: OpenChannel) {
        mFragmentListener.showConversation(channel)
    }

    // endregion

    companion object {
        fun newInstance() = ChannelListFragment()
    }

    interface Listener {
        fun showConversation(channel: OpenChannel)
    }
}