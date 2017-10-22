package com.xception.messaging.features.signin

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.xception.messaging.R
import com.xception.messaging.features.channels.ChannelsActivity
import com.xception.messaging.features.commons.BaseFragment

class SignInFragment: BaseFragment(), SignInView {

    lateinit var mSignInPresenter : SignInPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sign_in, container, false)

        mSignInPresenter = SignInPresenter(this)

        val signInInputEditText: TextInputEditText = view.findViewById(R.id.sign_in_text_input_edit_text)

        val signInButton: Button = view.findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener({
            mSignInPresenter.onLoginClicked(signInInputEditText.text.toString())
        })

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

    // region SignIn View

    override fun goToGeneralChannel() {
        startActivity(ChannelsActivity.newIntent(activity))
    }

    // endregion

    companion object {
        fun newInstance() : SignInFragment = SignInFragment()
    }
}