package com.gil.sample.service.api

import com.gil.sample.service.data.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface UserApi: BaseApi {

    @GET("/public/v2/users")
    fun fetchUsers() : Observable<List<User>>

}