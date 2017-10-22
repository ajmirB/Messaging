package com.xception.messaging.features.commons

import android.support.v4.app.Fragment
import android.widget.Toast
import com.xception.messaging.R
import com.xception.messaging.features.commons.presenter.AlertCommonErrorView


open class BaseFragment: Fragment(), AlertCommonErrorView {

    // region AlertCommonErrorView

    override fun alertUnknownError() {
        Toast.makeText(context, R.string.error_app_network_api_unknown, Toast.LENGTH_LONG).show()
    }

    override fun alertNetworkError() {
        Toast.makeText(context, R.string.error_network_service_missing, Toast.LENGTH_LONG).show()
    }

    // endregion
}
