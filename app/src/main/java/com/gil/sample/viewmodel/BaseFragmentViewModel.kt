package com.gil.sample.viewmodel

import androidx.annotation.CallSuper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gil.sample.model.BaseUserRepository
import com.gil.sample.service.data.AliveData
import timber.log.Timber

abstract class BaseFragmentViewModel() : ViewModel() {

    //private val mOnNext = MutableLiveData(false)
    //val onNext : LiveData<Boolean> = mOnNext

    val onNext = AliveData<Boolean>()

    fun onNext() {
        onNext.value = true
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        Timber.d("onCleared()")
        //repo.onCleared()
    }
}