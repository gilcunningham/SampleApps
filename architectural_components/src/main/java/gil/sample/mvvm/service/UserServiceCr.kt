package gil.sample.mvvm.service

import gil.sample.mvvm.service.api.UserApiCr
import gil.sample.mvvm.service.api.UserApiRx
import gil.sample.mvvm.service.data.User
import gil.sample.mvvm.service.helper.ApiHelper
import io.reactivex.rxjava3.plugins.RxJavaPlugins.onError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

/**
 * Users Service implemented with Coroutines.
 */
class UserServiceCr {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable)
    }

    //TODO : play around with member scope
    private val mServiceScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + exceptionHandler
    )

    // inject or pass in
    private val mUserApi = ApiHelper.instance(UserApiCr::class.java)

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

    suspend fun fetchUsers() : List<User> {
        return serviceCall {
            mUserApi.fetchUsers()
        }.orEmpty()
    }

    suspend fun <T> serviceCall(
        serviceDelegate: suspend () -> Response<T>
    ) : T? = withContext(Dispatchers.IO) {
        val response = serviceDelegate()
        // http level logging
        when (response.isSuccessful) {
            true -> {
                Timber.d("fetch users: success")
            }
            false -> {
                Timber.d("fetch users: failure")
                // perform logging etc
            }
        }
        response.body()
    }

    fun onCleared() {
        Timber.d("onCleared: service scope - ${mServiceScope.isActive}")
        mServiceScope.cancel()
        Timber.d("onCleared: service scope after cancel() - ${mServiceScope.isActive}")
    }
}