package com.gil.sample.viewmodel

import androidx.lifecycle.LiveData
import com.gil.sample.model.UserRepository
import com.gil.sample.service.data.User

class FirstFragmentViewModel: BaseFragmentViewModel() {

    val repo = UserRepository()

    val users: LiveData<List<User>>
        get() {
            return repo.users
        }

    // get list of users with Observable
    fun onClick1() {
        println("*** onclick 1")
        repo.getUsers()
    }
    fun onClick2() {
        println("*** onclick 2")
        repo.getUsers2()
    }

    fun onClick3() {
        println("*** onclick 3")
        repo.getUsers3()
    }
}