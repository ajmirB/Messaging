package com.xception.messaging.core.manager

import com.sendbird.android.SendBird
import com.sendbird.android.User
import io.reactivex.Single

class UserManager {

    /**
     * Sign in with a userId.
     * @userId the user id
     * @return the existing user or the new user created
     */
    fun signIn(userId: String): Single<User> {
        return Single.create<User> { subscriber ->
            SendBird.connect(userId, { user, e ->
                if (e != null) {
                    subscriber.onError(e)
                }
                subscriber.onSuccess(user)
            })
        }
    }
}