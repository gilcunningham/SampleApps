package gil.sample.mvvm.data.repo

import gil.sample.mvvm.data.dao.UserDao
import gil.sample.mvvm.data.model.User
import gil.sample.mvvm.data.service.UserServiceCr
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

/**
 * A [User]s repository.
 * Demonstrates Coroutines with LiveData and Flow functionality.
 *
 * TODO : add error handling
 */
@Singleton
class UsersRepositoryCr @Inject constructor(
    private val userService: UserServiceCr,
    private val userDao: UserDao
) : BaseUserRepository(userDao) {

    // TODO incorporate supervisor once we get longer running tasks
    //private val jobSupervisor = SupervisorJob()

    // internal data using Flow
    private val mUsersFlow = MutableStateFlow<List<User>>(listOf())

    // exposed data as Flow
    // Use this for longer running task TODO
    val usersFlow: Flow<List<User>> = mUsersFlow

    // flow demo
    val updateUsersFlow = flow {
        emit(userService.fetchUsers())
    }

    // update users
    suspend fun updateUsers() {
        updateUsers {
            val users = userService.fetchUsers()
            userDao.insertAll(users)
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
        //TODO: longer running tasks
        //Timber.d("onCleared: supervisor active ${jobSupervisor.isActive}")
        //jobSupervisor.cancel()
        //Timber.d("onCleared: supervisor active after cancel() ${jobSupervisor.isActive}")
        //userService.onCleared()
    }
}