package com.xception.messaging.features.channels.fragments

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.sendbird.android.BaseMessage
import com.xception.messaging.R
import com.xception.messaging.features.channels.items.ConversationController
import com.xception.messaging.features.channels.presenters.ConversationPresenter
import com.xception.messaging.features.channels.presenters.ConversationView
import com.xception.messaging.features.commons.BaseFragment
import com.xception.messaging.features.commons.UiThreadExecutor

class ConversationFragment: BaseFragment(), ConversationView {

    lateinit var mConversationPresenter: ConversationPresenter

    lateinit var mRecyclerView: EpoxyRecyclerView

    lateinit var mConversationController: ConversationController

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_conversation, container, false)

        val channelUrl = arguments.getString(CHANNEL_URL_KEY)

        mConversationPresenter = ConversationPresenter(this, channelUrl!!)

        mRecyclerView = view.findViewById(R.id.conversation_epoxy_recycler_view)

        // To display the item in the revers order
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        mRecyclerView.layoutManager = layoutManager

        // The controller is used for pagination, but it linked with the recycler view by the adapter
        mConversationController = ConversationController()
        mRecyclerView.adapter = mConversationController.adapter

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

    override fun showContent(dataSource: DataSource<Int, BaseMessage>) {
        val pagedList = PagedList.Builder<Int, BaseMessage>().run {
            setDataSource(dataSource)
            setMainThreadExecutor(UiThreadExecutor)
            setBackgroundThreadExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            setConfig(PagedList.Config.Builder().run {
                setEnablePlaceholders(false)
                setPageSize(20)
                setInitialLoadSizeHint(20)
                setPrefetchDistance(50)
                build()
            })
            build()
        }
        mConversationController.setList(pagedList)
    }

    // endregion

    companion object {

        val TAG = ConversationFragment::class.java.canonicalName

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