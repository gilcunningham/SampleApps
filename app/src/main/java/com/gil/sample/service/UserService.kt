package com.gil.sample.service

import androidx.lifecycle.MutableLiveData
import com.gil.sample.model.UserRepository
import com.gil.sample.service.api.UserApi
import com.gil.sample.service.data.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers

class UserService {

    private val userApi : UserApi
        get() = RetrofitHelper.getInstance().create(UserApi::class.java)

    fun fetchUsers(usersObserver: Observer<List<User>>) {
        userApi.fetchUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(usersObserver)
    }

    // observable -> livedata
    fun fetchUsers2(userLiveData: MutableLiveData<List<User>>) {

        val disposable = userApi.fetchUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users -> userLiveData.value = users },
                { error -> error.printStackTrace() },
                { println("on complete") },
            )
    }

    fun fetchUsers3(fetchUsers: (List<User>) -> Unit) {
        val disposable = userApi.fetchUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> fetchUsers(result) },
                { error -> error.printStackTrace() },
                { println("on complete") }
            )
    }
}