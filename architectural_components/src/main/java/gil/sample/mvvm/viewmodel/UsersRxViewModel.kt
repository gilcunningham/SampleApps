package gil.sample.mvvm.viewmodel

import androidx.lifecycle.LiveData
import gil.sample.mvvm.R
import gil.sample.mvvm.data.model.User
import gil.sample.mvvm.data.repo.UsersRepositoryRx
import timber.log.Timber

class UsersRxViewModel : BaseUsersViewModel() {

    // Left this without injection to compare
    private val mUserRepo = UsersRepositoryRx()

    // VM exposes the repo's [LiveData]
    val doingWork: LiveData<Boolean> = mUserRepo.doingWork.asLiveData()

    val users: LiveData<List<User>> = mUserRepo.users

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
        println("*** HERE")
        updateUsersDelegate()
    }
}