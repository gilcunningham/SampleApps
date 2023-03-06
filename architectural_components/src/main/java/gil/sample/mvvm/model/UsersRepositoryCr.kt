package gil.sample.mvvm.model

import gil.sample.mvvm.service.UserServiceCr
import gil.sample.mvvm.service.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * A [User]s repository.
 * Demonstrates different code styles with Coroutines.
 * 1. Anonymous Observer
 * 2. Anonymous Consumers (onNext / onError)
 * 3. Lambda expression
 * 4. Anonymous Consumer with Observable subscribe in repo
 *
 * TODO : error handling
 */
class UsersRepositoryCr : BaseUserRepository() {

    //TODO inject these or pass in
    private val mUserService = UserServiceCr()

    // TODO determine where applicable to use supervisor
    private val jobSupervisor = SupervisorJob()


    // internal data using Flow
    private val mUsersFlow = MutableStateFlow<List<User>>(listOf())

    // exposed data as Flow
    val usersFlow: Flow<List<User>> = mUsersFlow

    // flow demo
    val updateUsersFlow = flow {
        emit(mUserService.fetchUsers())
    }

    // fetch users
    suspend fun updateUsers() = withContext(Dispatchers.IO) {
        Timber.d("updateUsers()")
        users.value = mUserService.fetchUsers()
    }

    // fetch users with Flow
    // TODO: need to resolve adapter
    suspend fun updateUsersFlow() {
        Timber.d("updateUsersFlow()")
        mUserService
            .fetchUsersFlow()
            .map { users ->
                //TODO: remove
                Timber.d("*** users = $users")
                mUsersFlow.value = users
            }
    }

    @Override
    override fun onCleared() {
        Timber.d("onCleared: supervisor active ${jobSupervisor.isActive}")
        jobSupervisor.cancel()
        Timber.d("onCleared: supervisor active after cancel() ${jobSupervisor.isActive}")
        mUserService.onCleared()
    }
}