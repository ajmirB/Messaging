package com.xception.messaging.features.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xception.messaging.R
import com.xception.messaging.features.commons.BaseFragment

class SignInFragment : BaseFragment(), SignInView {

    lateinit var mSignInPresenter : SignInPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sign_in, container, false)

        mSignInPresenter = SignInPresenter(this)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSignInPresenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSignInPresenter.onViewDestroyed()
    }

    companion object {

        fun newInstance() : SignInFragment = SignInFragment()
    }
}