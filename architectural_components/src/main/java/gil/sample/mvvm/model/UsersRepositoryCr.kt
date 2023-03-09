package gil.sample.mvvm.model

import gil.sample.mvvm.service.UserServiceCr
import gil.sample.mvvm.service.data.User
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * A [User]s repository.
 * Demonstrates Coroutines with LiveData and Flow functionality.
 *
 * TODO : add error handling
 */
//@Singleton
class UsersRepositoryCr @Inject constructor(
    private val userService: UserServiceCr
) : BaseUserRepository() {

    // TODO incorporate supervisor once we get longer running tasks
    private val jobSupervisor = SupervisorJob()

    // internal data using Flow
    private val mUsersFlow = MutableStateFlow<List<User>>(listOf())

    // exposed data as Flow
    val usersFlow: Flow<List<User>> = mUsersFlow

    // flow demo
    val updateUsersFlow = flow {
        emit(userService.fetchUsers())
    }

    // update users
    suspend fun updateUsers() {
        updateUsers {
            users.value = userService.fetchUsers()
        }
    }

    // update Flow user
    suspend fun updateUsersFlow() {
        updateUsers {
            mUsersFlow.value = userService.fetchUsers()
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
        userService.onCleared()
    }
}