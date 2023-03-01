package com.gil.sample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gil.sample.model.UsersRepositoryRx

class BaseFragmentViewModelFactory : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersRxViewModel::class.java)) {
            return UsersRxViewModel() as T
        }

        throw NotImplementedError()
    }
}