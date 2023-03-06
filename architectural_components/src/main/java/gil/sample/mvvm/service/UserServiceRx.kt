package gil.sample.mvvm.service

import gil.sample.mvvm.service.api.UserApiRx
import gil.sample.mvvm.service.data.User
import gil.sample.mvvm.service.helper.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
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
        Timber.d("disposables size: ${mUserServiceDisposable.size()}")

        mUserServiceDisposable.add(
            userApiRx.fetchUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    onNext, onError
                )
        )

        Timber.d("disposables after add() size: ${mUserServiceDisposable.size()}")
    }

    /**
     * Fetch a list of users.
     * @param setUsers Function Lambda to set users.
     */
    fun fetchUsersLambda(setUsers: (List<User>) -> Unit) {
        Timber.d("disposables size: ${mUserServiceDisposable.size()}")

        mUserServiceDisposable.add(
            userApiRx.fetchUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { users -> setUsers(users) },
                    { error -> error.printStackTrace() } // TODO: better handling
                )
        )

        Timber.d("disposables after add() size: ${mUserServiceDisposable.size()}")
    }

    /**
     * Fetch a list of users.
     * @return [Observable][List] of [User]s.
     */
    fun fetchUsers() : Observable<List<User>> {
        return userApiRx.fetchUsers()
    }

    fun onCleared() {
        Timber.d("onCleared: disposables size: ${mUserServiceDisposable}")
        mUserServiceDisposable.clear()
        Timber.d("onCleared: disposable after clear() size: ${mUserServiceDisposable}")
    }
}