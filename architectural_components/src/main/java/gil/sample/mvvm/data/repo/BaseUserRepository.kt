package gil.sample.mvvm.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gil.sample.mvvm.AliveData
import gil.sample.mvvm.data.dao.UserDao
import gil.sample.mvvm.data.model.User
import java.lang.reflect.Array.get

abstract class BaseUserRepository(private val userDao: UserDao? = null) {

    // internal data - this could be initialized from db or some other source
    protected val mUsers = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() {
            println("*** users.get()")
            return userDao?.getUsers() ?: mUsers
        }

    // replaces the above ^ livedata / mutablelivedata pairing
    //var users: AliveData<List<User>> = AliveData()

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