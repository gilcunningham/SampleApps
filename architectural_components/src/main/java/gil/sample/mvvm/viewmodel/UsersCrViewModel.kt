package gil.sample.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gil.sample.mvvm.R
import gil.sample.mvvm.data.repo.UsersRepositoryCr
import gil.sample.mvvm.data.model.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class UsersCrViewModel @Inject internal constructor(
    private val userRepo: UsersRepositoryCr
) : BaseUsersViewModel() {

    //val userRepo = UsersRepositoryCr()

    // VM exposes the repo's [LiveData]
    val doingWork: LiveData<Boolean> = userRepo.doingWork.asLiveData()

    val users: LiveData<List<User>> = userRepo.users.asLiveData()

    // example with Flow
    val usersFlow: Flow<List<User>> = userRepo.usersFlow

    fun onUpdateUsersCr() {
        Timber.d("onUpdateUsersCr()")
        updateUsers {
            userRepo.updateUsers()
        }
    }

    fun onUpdateUsersCr2() {
        Timber.d("onUpdateUsersCr2()")
        viewModelScope.launch {
            userRepo.updateUsersFlow()
        }
    }

    fun onUpdateUsersCr3() {
        // TODO
    }

    private fun updateUsers(
        updateUsersDelegate: suspend () -> Unit
    ) {
        viewModelScope.launch {
            listHeader.value = R.string.users_listview_updating_label
            updateUsersDelegate()
        }
    }
}