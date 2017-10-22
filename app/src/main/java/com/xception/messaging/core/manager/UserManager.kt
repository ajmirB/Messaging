package com.xception.messaging.core.manager

import com.sendbird.android.SendBird
import com.sendbird.android.User
import com.xception.messaging.core.store.UserStore
import io.reactivex.Maybe
import io.reactivex.Single

class UserManager {

    val mUserStore = UserStore()

    /**
     * Sign in with a userId.
     * @userId the user id
     * @return the existing user or the new user created
     */
    fun signIn(userId: String) = Single.create<User>({ subscriber ->
            SendBird.connect(userId, { user, e ->
                if (e != null) {
                    subscriber.onError(e)
                }
                subscriber.onSuccess(user)
            })
        }).doOnSuccess({ user -> mUserStore.saveUser(user) })

    /**
     * Get the connected user if there is one
     * @return a maybe observable which contains the last user logged in the application
     * or complete immediately
     */
    fun getConnectedUser(): Maybe<User> = Maybe.defer({
        val user = mUserStore.getUser()

        if (user != null) {
            Maybe.just(user)
        } else {
            Maybe.empty()
        }
    })
}