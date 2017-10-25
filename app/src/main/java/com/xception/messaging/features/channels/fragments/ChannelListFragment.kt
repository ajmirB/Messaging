package com.xception.messaging.features.channels.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputEditText
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModel
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

        val mCreateChannelButton : FloatingActionButton = view.findViewById(R.id.channel_list_create_channel_button)
        mCreateChannelButton.setOnClickListener({ mChannelListPresenter.onAskToCreateChannelClicked()})

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mChannelListPresenter.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        mChannelListPresenter.onViewResumed()
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

    override fun updateContent(channels: List<ChannelItemData>) {
        // Generate the new models
        val channelModels = ArrayList<EpoxyModel<View>>(channels.size)
        channels.forEach {
            channelModels.add(
                    ChannelModel_()
                            .id(it.url)
                            .channel(it)
            )
        }
        // Update the models in the recycler view
        mRecyclerView.setModels(channelModels)
    }

    override fun showChannelCreationForm() {
        // Edit text to enter the channel name
        val input = TextInputEditText(activity)
        input.setHint(R.string.channel_creation_input_hint)
        input.maxLines = 1
        input.inputType = InputType.TYPE_CLASS_TEXT

        // Dialog to let the user enter its channel name
        AlertDialog.Builder(activity)
                .setTitle(R.string.channel_creation_title)
                .setView(input)
                .setPositiveButton(R.string.channel_creation_create_button, { _, _ -> mChannelListPresenter.onCreateChannelConfirmationClicked(input.text.toString()) })
                .setNegativeButton(R.string.channel_creation_cancel_button, { dialog, _ -> dialog.cancel() })
                .show()
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