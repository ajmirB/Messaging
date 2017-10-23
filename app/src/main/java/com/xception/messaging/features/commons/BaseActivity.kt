package com.xception.messaging.features.commons

import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.xception.messaging.R


open class BaseActivity: AppCompatActivity() {

    protected var mToolbarTitleTextView: TextView? = null

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        mToolbarTitleTextView?.text = title?.toString()?.toUpperCase()
    }

    /**
     * When the activity have a custom toolbar in activity xml, it configure to the default properties
     * @param toolbar the toolbar in the activity xml
     */
    protected fun setToolbarProperties(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        mToolbarTitleTextView = findViewById(R.id.toolbar_title_text_view)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val toolbarItemColor = ContextCompat.getColor(this, R.color.toolbar_item_color)
        toolbar.navigationIcon?.setColorFilter(toolbarItemColor, android.graphics.PorterDuff.Mode.MULTIPLY)
        toolbar.setNavigationOnClickListener({ onBackPressed() })
    }
}
