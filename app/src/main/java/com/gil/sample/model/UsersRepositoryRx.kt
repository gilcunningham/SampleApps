package com.gil.sample.model

import com.gil.sample.service.UserServiceRx
import com.gil.sample.service.data.User
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

class UsersRepositoryRx : BaseUserRepository() {

    private val mUserServiceRx = UserServiceRx()
    private val mUserRepoDisposables = CompositeDisposable()

    /**
     * Updates the value of [users].
     * This implementation makes use of a passed [Observer] to consume the [List] of [User]s.
     * In this example, the Repository is responsible for consuming the [Observable].
     */
    fun updateUsersWithObserver() {
        mUserServiceRx.fetchUsersObserver(
            object : Observer<List<User>> {
                override fun onNext(newUsers: List<User>) {
                    mUsers.value = newUsers.shuffled()
                    Timber.d("--> after fetchUsersObserver() disposables size: ${mUserRepoDisposables.size()}")
                }

                override fun onSubscribe(d: Disposable) {
                    mDoingWork.value = true
                    mUserRepoDisposables.add(d)
                }

                override fun onError(t: Throwable) {
                    Timber.d(t)
                }

                override fun onComplete() {
                    mDoingWork.value = false
                    Timber.d("onComplete")
                }
            }
        )
    }

    /**
     * Updates the value of [users].
     * This implementation makes use of a passed [Consumer]s to consume the [List] of [User]s.
     * In this example, the Repository is responsible for consuming the [Observable].
     */
    fun updateUsersWithConsumer() {
        mUserServiceRx.fetchUsersConsumer(
            { newUsers -> mUsers.value = newUsers },
            { throwable -> Timber.d(throwable) }
        )
    }

    /**
     * Updates the value of [users].
     * This implementation makes use of Closures and Lambda to set the [List] of [User]s.
     * In this example, the Service is responsible for consuming the [Observable].
     */
    fun updateUsersWithLambda() {
        mUserServiceRx.fetchUsersLambda { newUsers ->
            mUsers.value = newUsers
        }
    }

    @Override
    override fun onCleared() {
        mUserServiceRx.onCleared()
        Timber.d("onCleared: disposables size: ${mUserRepoDisposables.size()}")
        mUserRepoDisposables.clear()
        Timber.d("onCleared: disposable after clear(), size: ${mUserRepoDisposables.size()}")
    }
}