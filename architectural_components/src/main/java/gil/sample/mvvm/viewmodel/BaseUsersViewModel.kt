package gil.sample.mvvm.viewmodel

import androidx.annotation.CallSuper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gil.sample.mvvm.R
import gil.sample.mvvm.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseUsersViewModel : ViewModel() {

    // list header
    val listHeader = mutableStateOf(R.string.users_listview_header_label)

    private var mOnNext = MutableLiveData<Boolean>()
    val onNext: LiveData<Boolean> = mOnNext

    fun onNext() {
        Timber.d("onNext()")
        mOnNext.value = true
    }

    private val mOnUserSelected = MutableSharedFlow<User>()
    val onUserSelected = mOnUserSelected.asSharedFlow()

    fun onUserSelected(user: User) {
        Timber.d("onUserSelected() - $user")
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