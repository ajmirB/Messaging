package com.xception.messaging.features.channels.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sendbird.android.BaseMessage
import com.sendbird.android.UserMessage
import com.xception.messaging.R
import com.xception.messaging.features.channels.items.MessageMeModel_
import com.xception.messaging.features.channels.presenters.ConversationPresenter
import com.xception.messaging.features.channels.presenters.ConversationView
import com.xception.messaging.features.commons.BaseFragment
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder


class ConversationFragment: BaseFragment(), ConversationView {

    lateinit var mConversationPresenter: ConversationPresenter

    lateinit var mPaginate: Paginate

    lateinit var mRecyclerView: EpoxyRecyclerView

    lateinit var mMessageInputText: EditText

    lateinit var mSendMessageButton: ImageView

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

    // region ConversationView

    override fun initContent(messages: List<BaseMessage>) {
        mRecyclerView.buildModelsWith { controller ->
            messages.forEach {
                if (it is UserMessage) {
                    MessageMeModel_()
                            .id(it.messageId)
                            .message(it)
                            .addTo(controller)
                }
            }
        }

        mPaginate = PaginateBuilder()
                .with(mRecyclerView)
                .setCallback({ mConversationPresenter.onLoadingMore() })
                .setLoadingTriggerThreshold(5)
                .build()
    }

    override fun updateContent(messages: List<BaseMessage>) {
        // Generate the new models
        val messageModel = ArrayList<EpoxyModel<View>>(messages.size)
        messages.forEach {
            if (it is UserMessage) {
                messageModel.add(
                        MessageMeModel_()
                        .id(it.messageId)
                        .message(it)
                )
            }
        }
        // Update the recycler view
        mRecyclerView.setModels(messageModel)
    }

    override fun stopPaginate() {
        mPaginate.setPaginateNoMoreItems(true)
    }

    override fun resetInputText() {
        mMessageInputText.text = null
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
}