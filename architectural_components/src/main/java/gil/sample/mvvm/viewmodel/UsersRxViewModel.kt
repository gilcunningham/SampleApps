package gil.sample.mvvm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import gil.sample.mvvm.R
import gil.sample.mvvm.model.UsersRepositoryRx
import gil.sample.mvvm.service.data.User
import timber.log.Timber

class UsersRxViewModel : BaseUsersViewModel() {

    // TODO: Hilt or pass-in
    private val mUserRepo = UsersRepositoryRx()

    // VM exposes the repo's [LiveData]
    val doingWork: LiveData<Boolean> = mUserRepo.doingWork.asLiveData()

    val users: LiveData<List<User>> = mUserRepo.users.asLiveData()

    fun onUpdateUsersRx() {
        updateUsers("onUpdateUsersRx()") {
            mUserRepo.updateUsers()
        }
    }

    fun onUpdateUsersRx2() {
        updateUsers("onUpdateUsersRx2()") {
            mUserRepo.updateUsersWithObserver()
        }
    }

    fun onUpdateUsersRx3() {
        updateUsers("onUpdateUsersRx3()") {
            mUserRepo.updateUsersWithConsumer()
        }
    }

    fun onUpdateUsersRx4() {
        updateUsers("onUpdateUsersRx4()") {
            mUserRepo.updateUsersWithLambda()
        }
    }

    private fun updateUsers(
        msg: String? = null,
        updateUsersDelegate: () -> Unit
    ) {
        msg?.let { Timber.d(it) }
        listHeader.value = R.string.users_listview_updating_label
        updateUsersDelegate()
    }
}