package com.xception.messaging.features.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xception.messaging.R
import com.xception.messaging.features.commons.BaseFragment
import com.xception.messaging.features.signin.SignInFragment
import com.xception.messaging.features.signin.SignInPresenter

/**
 * Created by Ajmir Busgeeth on 22/10/2017.
 */
class ConversationFragment : BaseFragment(), ConversationView {

    lateinit var mConversationPresenter: ConversationPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sign_in, container, false)

        mConversationPresenter = ConversationPresenter(this)

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

        fun newInstance() : SignInFragment = SignInFragment()
    }
}