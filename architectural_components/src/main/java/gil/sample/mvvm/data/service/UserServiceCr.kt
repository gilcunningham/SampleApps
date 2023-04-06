package gil.sample.mvvm.data.service

import gil.sample.mvvm.data.model.User
import gil.sample.mvvm.data.service.api.UserApiCr
import io.reactivex.rxjava3.plugins.RxJavaPlugins.onError
import javax.inject.Inject
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
class UserServiceCr @Inject constructor(
    private val userApi: UserApiCr
) {
    //TODO : longer running service
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable)
    }

    private val mServiceScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + exceptionHandler
    )

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

    /**
     * Fetch a list of users.
     * @return List<User> //TODO: make livedata
     */
    suspend fun fetchUsers(): List<User> {
        return serviceCall {
            userApi.fetchUsers()
        }.orEmpty()
    }

    /**
     * Generic service call that takes a [Response] producing suspending function returns the
     * [Response] body as [T].
     * @param serviceDelegate A suspending function that produces a [Response] type T.
     * @return The [Response] body [T]
     */
    protected suspend fun <T> serviceCall(
        serviceDelegate: suspend () -> Response<T>
    ): T? = withContext(Dispatchers.IO) {
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

    //TODO: long running coroutine service
    //fun onCleared() {
    //    Timber.d("onCleared: service scope - ${mServiceScope.isActive}")
    //    mServiceScope.cancel()
    //    Timber.d("onCleared: service scope after cancel() - ${mServiceScope.isActive}")
    //}
}