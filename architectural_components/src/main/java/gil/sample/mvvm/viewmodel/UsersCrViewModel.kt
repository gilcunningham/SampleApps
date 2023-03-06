package gil.sample.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import gil.sample.mvvm.model.UsersRepositoryCr
import gil.sample.mvvm.service.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class UsersCrViewModel : BaseUsersViewModel() {

    val userRepo = UsersRepositoryCr()

    // VM exposes the repos data
    val users: LiveData<List<User>> = userRepo.users.asLiveData()

    // example with Flow
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