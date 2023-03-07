package gil.sample.mvvm.model

import gil.sample.mvvm.service.UserServiceCr
import gil.sample.mvvm.service.data.User
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * A [User]s repository.
 * Demonstrates Coroutines with LiveData and Flow functionality.
 *
 * TODO : error handling
 */
class UsersRepositoryCr : BaseUserRepository() {

    //TODO inject or pass in
    private val mUserService = UserServiceCr()

    // TODO incorporate supervisor
    private val jobSupervisor = SupervisorJob()

    // internal data using Flow
    private val mUsersFlow = MutableStateFlow<List<User>>(listOf())

    // exposed data as Flow
    val usersFlow: Flow<List<User>> = mUsersFlow

    // flow demo
    val updateUsersFlow = flow {
        emit(mUserService.fetchUsers())
    }

    // update users
    suspend fun updateUsers() {
        updateUsers {
            users.value = mUserService.fetchUsers()
        }
    }

    // update Flow user
    suspend fun updateUsersFlow() {
        Timber.d("updateUsersFlow()")
        updateUsers {
            mUsersFlow.value = mUserService.fetchUsers()
        }
    }

    suspend fun updateUsers(updateUsersDelegate: suspend () -> Unit) {
        doingWork.value = true
        updateUsersDelegate() // blocking

        doingWork.value = false
    }

    @Override
    override fun onCleared() {
        Timber.d("onCleared: supervisor active ${jobSupervisor.isActive}")
        jobSupervisor.cancel()
        Timber.d("onCleared: supervisor active after cancel() ${jobSupervisor.isActive}")
        mUserService.onCleared()
    }
}