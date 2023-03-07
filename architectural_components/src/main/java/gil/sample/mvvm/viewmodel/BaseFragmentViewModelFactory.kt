package gil.sample.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseFragmentViewModelFactory : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersRxViewModel::class.java)) {
            return UsersRxViewModel() as T
        }

        throw NotImplementedError()
    }
}