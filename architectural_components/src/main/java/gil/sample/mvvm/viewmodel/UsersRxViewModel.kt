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

    // list header
    val listHeader = mutableStateOf(R.string.users_listview_header_label)

    // VM exposes the repo's [LiveData]
    val doingWork: LiveData<Boolean> = mUserRepo.doingWork.asLiveData()
    val users: LiveData<List<User>> = mUserRepo.users.asLiveData()

    fun onUpdateUsersRx() {
        updateUsersImpl("onUpdateUsersRx()") {
            mUserRepo.updateUsers()
        }
    }

    fun onUpdateUsersRx2() {
        updateUsersImpl("onUpdateUsersRx2()") {
            mUserRepo.updateUsersWithObserver()
        }
    }

    fun onUpdateUsersRx3() {
        updateUsersImpl("onUpdateUsersRx3()") {
            mUserRepo.updateUsersWithConsumer()
        }
    }

    fun onUpdateUsersRx4() {
        updateUsersImpl("onUpdateUsersRx4()") {
            mUserRepo.updateUsersWithLambda()
        }
    }

    private fun updateUsersImpl(msg: String? = null, updateUsers: () -> Unit) {
        msg?.let { Timber.d(it) }
        listHeader.value = R.string.users_listview_updating_label
        updateUsers()
    }
}