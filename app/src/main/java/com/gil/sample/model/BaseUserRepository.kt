package com.gil.sample.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gil.sample.service.data.User

abstract class BaseUserRepository {

    // internal data - this could be initialized from db or some other source
    protected val mUsers = MutableLiveData<List<User>>()

    // exposed data
    val users: LiveData<List<User>> = mUsers

    /**
     * Enforce cleanup.
     */
    abstract fun onCleared()
}