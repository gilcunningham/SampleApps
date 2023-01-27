package com.example.sampleapplication.api

import com.example.sampleapplication.model.User
import rx.Observable
import retrofit2.http.POST

interface UserApi {

    @POST("/public/v2/users")
    fun fetchUsers() : Observable<List<User>>

}