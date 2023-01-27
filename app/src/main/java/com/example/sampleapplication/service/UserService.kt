package com.example.sampleapplication.service

import com.example.sampleapplication.api.UserApi

class UserService {
    fun fetchUsers() { //}: <List<User>> {
        val userApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        userApi.fetchUsers()
            .doOnNext { users ->
                users.forEach { user ->
                    println("User : $user")
                }
            }
    }
}