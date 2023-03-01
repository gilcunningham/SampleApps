package com.gil.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gil.sample.model.UsersRepositoryCr
import com.gil.sample.service.data.AliveData
import com.gil.sample.service.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class UsersCrViewModel : BaseFragmentViewModel() {

    val userRepo = UsersRepositoryCr()

    // VM exposes the repos data
    val users: LiveData<List<User>>
        get() {
            println("*** get users")
            return userRepo.users
        }

    val usersFlow: Flow<List<User>>
        get() {
            println("*** get usersFlow")
            return userRepo.usersFlow
        }

    fun onUpdateUsersKt() {
        Timber.d("onUpdateUsersKt()")
        viewModelScope.launch {
            userRepo.updateUsers()
        }
    }

    fun onUpdateUsersKt2() {
        Timber.d("onUpdateUsersKt2()")
        viewModelScope.launch {
            userRepo.updateUsersFlow()
        }
    }

    fun onUpdateUsersKt3() {
        // TODO
    }
}