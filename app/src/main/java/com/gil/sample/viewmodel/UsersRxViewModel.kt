package com.gil.sample.viewmodel

import android.app.Notification.Action
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.gil.sample.model.UsersRepositoryRx
import com.gil.sample.service.data.AliveData
import com.gil.sample.service.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class UsersRxViewModel : BaseFragmentViewModel() {

    // TODO: Hilt or pass-in
    private val mUserRepo = UsersRepositoryRx()

    val listHeader = mutableStateOf("Users")

    val doingWork: LiveData<Boolean>
        get() {
            return mUserRepo.doingWork
        }

    // VM exposes the repo's [LiveData]
    val usersRx: LiveData<List<User>>
        get() {
            return mUserRepo.users
        }

    // handle fetch
    fun onUpdateUsersRx() {
        Timber.d("onUpdateUsersRx()")
        updateUsers { mUserRepo.updateUsersWithObserver() }
    }

    fun onUpdateUsersRx2() {
        Timber.d("onUpdateUsersRx2()")
        mUserRepo.updateUsersWithConsumer()
    }

    fun onUpdateUsersRx3() {
        Timber.d("onUpdateUsersRx3()")
        mUserRepo.updateUsersWithLambda()
    }

    private fun updateUsers(action: () -> Unit) {
        listHeader.value = "Updating ..."
        action.invoke()
    }
}