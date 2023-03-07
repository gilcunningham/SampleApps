package gil.sample.mvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gil.sample.mvvm.service.data.AliveData
import gil.sample.mvvm.service.data.User

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