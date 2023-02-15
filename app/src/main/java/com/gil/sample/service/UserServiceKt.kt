package com.gil.sample.service

import android.os.Build
import androidx.annotation.RequiresApi
import com.gil.sample.service.api.UserApiKt
import com.gil.sample.service.data.User
import com.gil.sample.service.helper.RetrofitHelper
import io.reactivex.rxjava3.plugins.RxJavaPlugins.onError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

class UserServiceKt {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable)
    }

    private val mServiceScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + exceptionHandler)

    private val mUserApi = RetrofitHelper.instance(UserApiKt::class.java)

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