package com.gil.sample.service.api;

import com.gil.sample.service.data.User
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface UserApiKt : BaseApi {

    @GET("/public/v2/users")
    suspend fun fetchUsers(): List<User>

    //TODO get adapter for Flow
    @GET("/public/v2/users")
    suspend fun fetchUsersFlow(): Flow<List<User>>
}
