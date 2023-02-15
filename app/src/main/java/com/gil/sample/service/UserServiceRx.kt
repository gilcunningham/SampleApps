package com.gil.sample.service

import com.gil.sample.service.api.UserApiRx
import com.gil.sample.service.data.User
import com.gil.sample.service.helper.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class UserServiceRx {

    private val mUserServiceDisposable = CompositeDisposable()

    private val userApiRx : UserApiRx
        get() = RetrofitHelper.instance(UserApiRx::class.java)

    fun fetchUsersObserver(usersObserver: Observer<List<User>>) {
        userApiRx.fetchUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(usersObserver)
    }

    fun fetchUsersConsumer(
        onNext: Consumer<List<User>>,
        onError: Consumer<Throwable>,
    ) {
        Timber.d("disposables size: ${mUserServiceDisposable}")

        mUserServiceDisposable.add(
            userApiRx.fetchUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    onNext, onError
                )
        )

        Timber.d("disposables after add(), size: ${mUserServiceDisposable}")
    }

    /**
     * Fetch a list of users.
     * @param fetchUsers Lambda to set users.
     */
    fun fetchUsersLambda(setUsers: (List<User>) -> Unit) {
        Timber.d("disposables size: ${mUserServiceDisposable}")

        mUserServiceDisposable.add(
            userApiRx.fetchUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { users -> setUsers(users) },
                    { error -> error.printStackTrace() }
                )
        )

        Timber.d("disposables after add(), size: ${mUserServiceDisposable}")
    }

    fun onCleared() {
        Timber.d("onCleared: disposables size: ${mUserServiceDisposable}")
        mUserServiceDisposable.clear()
        Timber.d("onCleared: disposable after clear(), size: ${mUserServiceDisposable}")
    }
}