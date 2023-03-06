package gil.sample.mvvm.model

import gil.sample.mvvm.service.UserServiceKt
import gil.sample.mvvm.service.data.User
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class UsersRepositoryCr : BaseUserRepository() {

    //TODO inject these or pass in
    private val mUserService = UserServiceKt()

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
    suspend fun updateUsers() {
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