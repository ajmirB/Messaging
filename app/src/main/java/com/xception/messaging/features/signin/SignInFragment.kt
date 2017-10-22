package com.xception.messaging.features.signin

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.xception.messaging.R
import com.xception.messaging.features.channels.ChannelsActivity
import com.xception.messaging.features.commons.BaseFragment

class SignInFragment: BaseFragment(), SignInView {

    lateinit var mSignInPresenter : SignInPresenter

    lateinit var mInputEditText: TextInputEditText

    lateinit var mButton: AppCompatButton

    lateinit var mProgressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sign_in, container, false)

        mSignInPresenter = SignInPresenter(this)

        mInputEditText = view.findViewById(R.id.sign_in_text_input_edit_text)

        mButton = view.findViewById(R.id.sign_in_button)
        mButton.setOnClickListener({
            mSignInPresenter.onLoginClicked(mInputEditText.text.toString())
        })

        mProgressBar = view.findViewById(R.id.sign_in_progress_bar)

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

    override fun prefillNickname(nickname: String) {
        mInputEditText.setText(nickname, TextView.BufferType.EDITABLE)
    }

    override fun goToGeneralChannel() {
        startActivity(ChannelsActivity.newIntent(activity))
    }

    // endregion

    // region Loading View

    override fun showLoadingView() {
        mInputEditText.isEnabled = false
        mInputEditText.animate().alpha(0.5f)

        mButton.isEnabled = false
        mButton.animate().alpha(0.5f)

        mProgressBar.visibility = View.VISIBLE
        mProgressBar.alpha = 0f
        mProgressBar.animate().alpha(1f)
    }

    override fun hideLoadingView() {
        mInputEditText.isEnabled = true
        mInputEditText.animate().alpha(1f)

        mButton.isEnabled = true
        mButton.animate().alpha(1f)

        mProgressBar.visibility = View.INVISIBLE
    }

    // end region

    companion object {
        fun newInstance() = SignInFragment()
    }
}