package gil.sample.mvvm.data.service

import gil.sample.mvvm.data.service.api.UserApiCr
import gil.sample.mvvm.data.model.User
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

    // TODO
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable)
    }

    //TODO : play around with member scope
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

    suspend fun fetchUsers(): List<User> {
        return serviceCall {
            userApi.fetchUsers()
        }.orEmpty()
    }

    suspend fun <T> serviceCall(
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

    fun onCleared() {
        Timber.d("onCleared: service scope - ${mServiceScope.isActive}")
        mServiceScope.cancel()
        Timber.d("onCleared: service scope after cancel() - ${mServiceScope.isActive}")
    }
}