package gil.sample.mvvm.data.repo

import gil.sample.mvvm.AliveData
import gil.sample.mvvm.data.model.User

abstract class BaseUserRepository {

    // internal data - this could be initialized from db or some other source
    //protected val mUsers = MutableLiveData<List<User>>()
    //val users: LiveData<List<User>> = mUsers

    // replaces the above ^ livedata / mutablelivedata pairing
    val users: AliveData<List<User>> = AliveData()

    // internal data - indicates repo is working
    //protected val mDoingWork = MutableLiveData<Boolean>()
    //val doingWork: LiveData<Boolean> = mDoingWork

    // replaces the above ^ livedata / mutablelivedata pairing
    val doingWork = AliveData(false)

    /**
     * Enforce cleanup.
     */
    abstract fun onCleared()
}