package com.gil.sample.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gil.sample.service.UserService
import com.gil.sample.service.data.User
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class UserRepository {

    private val userService = UserService()

    private var mUsers = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = mUsers

    fun getUsers() {
        userService.fetchUsers(
            object : Observer<List<User>> {
                override fun onNext(users: List<User>) { mUsers.value = users }
                override fun onSubscribe(d: Disposable) { println("*** subscribed") }
                override fun onError(e: Throwable) { e.printStackTrace() }
                override fun onComplete() { println("*** onComplete") }
            }
        )
    }

    fun getUsers2() {
        userService.fetchUsers2(mUsers)
    }

    fun getUsers3() {
        userService.fetchUsers3 { users ->
            mUsers.value = users
        }
    }

}