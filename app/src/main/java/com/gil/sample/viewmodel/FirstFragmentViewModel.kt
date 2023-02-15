package com.gil.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gil.sample.model.UserRepositoryKt
import com.gil.sample.model.UserRepositoryRx
import com.gil.sample.service.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import timber.log.Timber

class FirstFragmentViewModel : BaseFragmentViewModel() {

    // TODO, inject this or pass in
    private val mUserRepo = UserRepositoryRx()
    private val mUserRepo2 = UserRepositoryKt()

    // VM exposes the data as [LiveData]
    val usersRx: LiveData<List<User>>
        get() {
            return mUserRepo.users
        }

    val usersCr: LiveData<List<User>>
        get() {
            return mUserRepo2.users
        }

    val usersFlow: Flow<List<User>>
        get() {
            println("*** get users2")
            return mUserRepo2.usersFlow
        }

    // VM exposes the data as [LiveData]

    // handle fetch
    fun onUpdateUsersRx() {
        Timber.d("NEW onUpdateUsersRx()")
        mUserRepo.updateUsersObserver()
    }

    fun onUpdateUsersRx2() {
        Timber.d("NEW onUpdateUsersRx()")
        mUserRepo.updateUsersConsumer()
    }

    fun onUpdateUsersRx3() {
        Timber.d("onUpdateUsersRx2()")
        mUserRepo.updateUsersLambda()
    }

    fun onUpdateUsersKt() {
        Timber.d("onUpdateUsersKt()")
        viewModelScope.launch {
            mUserRepo2.updateUsers()
        }
    }

    fun onUpdateUsersKt2() {
        Timber.d("onUpdateUsersKt2()")
        viewModelScope.launch {
            mUserRepo2.updateUsersFlow()
        }
    }

    fun onUpdateUsersKt3() {
        // TODO
    }

    override fun onCleared() {
        Timber.d("*** onCleared")
        super.onCleared()
        mUserRepo.onCleared()
        mUserRepo2.onCleared()
    }
}