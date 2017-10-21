package com.xception.messaging.features.signin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.xception.messaging.R
import com.xception.messaging.features.commons.BaseActivity

class SignInActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        showSignIn()
    }

    fun showSignIn(){
        val signInFragment = SignInFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_main_container, signInFragment)
                .commit()
    }
}
