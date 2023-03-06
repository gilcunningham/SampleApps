package gil.sample.mvvm.service

import gil.sample.mvvm.service.api.UserApiCr
import gil.sample.mvvm.service.data.User
import gil.sample.mvvm.service.helper.RetrofitHelper
import io.reactivex.rxjava3.plugins.RxJavaPlugins.onError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Users Service implemented with Coroutines.
 */
class UserServiceCr {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable)
    }

    private val mServiceScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + exceptionHandler
    )

    private val mUserApi = RetrofitHelper.instance(UserApiCr::class.java)

    /** TODO: adapter for Result
    suspend fun fetchUsers() : Result<List<User>> {
    val result = mUserApi.fetchUsers()
    when (result.isSuccess) {
    true -> { Timber.d("fetch users: success") }
    false -> { Timber.d("fetch users: failure") }
    }
    return result
    }
     **/

    suspend fun fetchUsers() = withContext(Dispatchers.Default) {
        mUserApi.fetchUsers().shuffled()
    }

    suspend fun fetchUsersResult() : List<User> {
        val result = mUserApi.fetchUsersResult()
        when (result.isSuccess) {
            true -> { Timber.d("fetch users: success") }
            false -> { Timber.d("fetch users: failure") }
        }
        return result.getOrDefault(listOf())
    }

    //TODO: flow
    suspend fun fetchUsersFlow() = withContext(Dispatchers.Default) {
        mUserApi.fetchUsersFlow()
    }

    fun onCleared() {
        Timber.d("onCleared: service scope - ${mServiceScope.isActive}")
        mServiceScope.cancel()
        Timber.d("onCleared: service scope after cancel() - ${mServiceScope.isActive}")
    }
}