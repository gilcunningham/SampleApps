package gil.sample.mvvm.data.repo

import gil.sample.mvvm.data.service.UserServiceRx
import gil.sample.mvvm.data.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * A [User]s repository.
 * Demonstrates different code styles of Observer / Observables.
 * 1. Anonymous Observer
 * 2. Anonymous Consumers (onNext / onError)
 * 3. Lambda expression
 * 4. Anonymous Consumer with Observable subscribe in repo
 *
 * TODO: add error handling
 */
class UsersRepositoryRx : BaseUserRepository() {

    // TODO: pass in with factory or use Hilt
    private val mUserServiceRx = UserServiceRx()

    private val mUserDisposables = CompositeDisposable()

    /**
     * Updates the [users].
     * This implementation uses an anonymous [Observer] to consume the [Observable].
     */
    fun updateUsersWithObserver() {
        updateUsers {
            mUserServiceRx.fetchUsersObserver(
                object : Observer<List<User>> {
                    override fun onNext(newUsers: List<User>) {
                        mUsers.value = newUsers.shuffled()
                        Timber.d("--> after fetchUsersObserver() disposables size: ${mUserDisposables.size()}")
                    }

                    override fun onSubscribe(d: Disposable) {
                        mUserDisposables.add(d)
                    }

                    override fun onError(t: Throwable) { //TODO: improve
                        Timber.d(t)
                    }

                    override fun onComplete() {
                        doingWork.value = false
                        Timber.d("onComplete")
                    }
                }
            )
        }
    }

    /**
     * Update the [users].
     * This implementation uses anonymous [Consumer]s to consume the [Observable].
     */
    fun updateUsersWithConsumer() {
        updateUsers {
            mUserServiceRx.fetchUsersConsumer(
                { newUsers ->
                    mUsers.value = newUsers.shuffled()
                    doingWork.value = false
                },
                { throwable -> Timber.d(throwable) }
            )
        }
    }

    /**
     * Update the [users].
     * This implementation uses a lambda to set the [List] of [User]s.
     * Note: The service is responsible for error handling.
     */
    fun updateUsersWithLambda() {
        updateUsers {
            mUserServiceRx.fetchUsersLambda { newUsers ->
                mUsers.value = newUsers.shuffled()
                doingWork.value = false
            }
        }
    }

    /**
     * Update the [users].
     * This implementation subscribes to the Observable and consumes onNext and onError.
     */
    fun updateUsers() {
        updateUsers {
            mUserDisposables.add(
                mUserServiceRx.fetchUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { newUsers ->
                            mUsers.value = newUsers.shuffled()
                            doingWork.value = false
                        },
                        { error -> error.printStackTrace() } // TODO: better handling
                    )
            )
        }
    }

    @Override
    override fun onCleared() {
        mUserServiceRx.onCleared()
        Timber.d("onCleared: disposables size: ${mUserDisposables.size()}")
        mUserDisposables.clear()
        Timber.d("onCleared: disposable after clear(), size: ${mUserDisposables.size()}")
    }

    /**
     * Update users utility method.
     * @param updateUsers Update users lambda.
     */
    private fun updateUsers(updateUsersDelegate: () -> Unit) {
        doingWork.value = true
        updateUsersDelegate()
    }

}