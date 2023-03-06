package gil.sample.mvvm.viewmodel

import androidx.annotation.CallSuper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gil.sample.mvvm.service.data.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseUsersViewModel : ViewModel() {

    private var mOnNext = MutableLiveData<Boolean>()
    val onNext: LiveData<Boolean> = mOnNext

    fun onNext() {
        mOnNext.value = true
    }

    private val mOnUserSelected = MutableSharedFlow<User>()
    val onUserSelected = mOnUserSelected.asSharedFlow()

    fun onUserSelected(user: User) {
        Timber.d("onUserSelected - $user")
        viewModelScope.launch {
            mOnUserSelected.emit(user)
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        Timber.d("onCleared()")
        //repo.onCleared()
    }
}