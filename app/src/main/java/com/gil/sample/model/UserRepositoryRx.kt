package com.gil.sample.model

import com.gil.sample.service.UserServiceRx
import com.gil.sample.service.data.User
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

class UserRepositoryRx : BaseUserRepository() {

    private val mUserServiceRx = UserServiceRx()
    private val mUserRepoDisposables = CompositeDisposable()

    /**
     * Updates the value of [users].
     * This implementation makes use of a passed [Observer] to consume the [List] of [User]s.
     * In this example, the Repository is responsible for consuming the [Observable].
     */
    fun updateUsersObserver() {
        mUserServiceRx.fetchUsersObserver(
            object : Observer<List<User>> {
                override fun onNext(users: List<User>) {
                    mUsers.value = users
                }

                override fun onSubscribe(d: Disposable) {
                    mUserRepoDisposables.add(d)
                }

                override fun onError(t: Throwable) {
                    Timber.d(t)
                }

                override fun onComplete() {
                    Timber.d("onComplete")
                }
            }
        )
    }

    /**
     * Updates the value of [users].
     * This implementation makes use of a passed [Observer] to consume the [List] of [User]s.
     * In this example, the Repository is responsible for consuming the [Observable].
     */
    fun updateUsersConsumer() {
        mUserServiceRx.fetchUsersConsumer(
            { users -> mUsers.value = users },
            { throwable -> Timber.d(throwable) }
        )
    }

    /**
     * Updates the value of [users].
     * This implementation makes use of Closures and Lambda to set the [List] of [User]s.
     * In this example, the Service is responsible for consuming the [Observable].
     */
    fun updateUsersLambda() {
        mUserServiceRx.fetchUsersLambda { users ->
            mUsers.value = users
        }
    }

    @Override
    override fun onCleared() {
        mUserServiceRx.onCleared()
        Timber.d("onCleared: disposables size: ${mUserRepoDisposables}")
        mUserRepoDisposables.clear()
        Timber.d("onCleared: disposable after clear(), size: ${mUserRepoDisposables}")
    }
}