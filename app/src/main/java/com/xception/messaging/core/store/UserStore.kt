package com.xception.messaging.core.store

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.sendbird.android.User
import com.xception.messaging.helper.ApplicationHelper.context
import com.xception.messaging.helper.ApplicationHelper.gson

open class UserStore {

    private var mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Save the user in the shared preferences
     */
    fun saveUser(user: User) {
        mPreferences.edit()
                .putString(APP_USER_KEY, gson.toJson(user))
                .apply()
    }

    /**
     * Get user in the shared preferences
     * @return the user in the shared preferences, or null if no user stored
     */
    fun getUser(): User? {
        val userJson = mPreferences.getString(APP_USER_KEY, "")

        return if (userJson.isEmpty()) {
            null
        } else {
            gson.fromJson(userJson, User::class.java)
        }
    }

    companion object {
        val APP_USER_KEY = "app::user"
    }
}
