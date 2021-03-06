package com.xception.messaging.features.channels.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sendbird.android.BaseChannel
import com.xception.messaging.R
import com.xception.messaging.features.channels.presenters.ConversationPresenter
import com.xception.messaging.features.channels.presenters.ConversationView
import com.xception.messaging.features.channels.presenters.MessageItemData
import com.xception.messaging.features.commons.BaseFragment
import com.xception.messaging.helper.toModel
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder




class ConversationFragment: BaseFragment(), ConversationView {

    lateinit var mConversationPresenter: ConversationPresenter

    lateinit var mPaginate: Paginate

    lateinit var mRecyclerView: EpoxyRecyclerView

    lateinit var mMessageInputText: EditText

    lateinit var mSendMessageButton: ImageView

    lateinit var mFragmentListener: Listener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is Listener) {
            mFragmentListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_conversation, container, false)

        val channelUrl = arguments.getString(CHANNEL_URL_KEY)

        mConversationPresenter = ConversationPresenter(this, channelUrl!!)

        mRecyclerView = view.findViewById(R.id.conversation_epoxy_recycler_view)

        // To display the item in the reverse order
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        mRecyclerView.layoutManager = layoutManager

        mMessageInputText = view.findViewById(R.id.conversation_epoxy_input_edit_text_view)

        mSendMessageButton = view.findViewById(R.id.conversation_send_image_view)
        mSendMessageButton.setOnClickListener({ mConversationPresenter.onSendButtonClicked(mMessageInputText.text.toString())  })

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mConversationPresenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setHasOptionsMenu(false)
        mConversationPresenter.onViewDestroyed()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_conversation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.conversation_participants_button -> {
                mConversationPresenter.onParticipantsListClick()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // region ConversationView

    override fun initContent(messages: List<MessageItemData>) {
        mRecyclerView.buildModelsWith { controller ->
            messages.toModel().forEach {
                it.addTo(controller)
            }
        }

        mPaginate = PaginateBuilder()
                .with(mRecyclerView)
                .setCallback({ mConversationPresenter.onLoadingMore() })
                .setLoadingTriggerThreshold(5)
                .build()
    }

    override fun addPreviousMessages(messages: List<MessageItemData>) {
        mRecyclerView.setModels(messages.toModel())
    }

    override fun addIncomingMessage(messages: List<MessageItemData>) {
        mRecyclerView.setModels(messages.toModel())
        mRecyclerView.smoothScrollToPosition(0)
    }

    override fun stopPaginate() {
        mPaginate.setPaginateNoMoreItems(true)
    }

    override fun resetInputText() {
        mMessageInputText.text = null
    }

    override fun goToParticipants(channel: BaseChannel) {
       mFragmentListener.showParticipants(channel)
    }

    // endregion

    companion object {

        val CHANNEL_URL_KEY = "CHANNEL_URL_KEY"

        fun newInstance(channelUrl: String): ConversationFragment {
            val bundle = Bundle()
            bundle.putString(CHANNEL_URL_KEY, channelUrl)

            val fragment = ConversationFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    interface Listener {
        fun showParticipants(channel: BaseChannel)
    }
}