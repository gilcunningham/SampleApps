package gil.sample.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gil.sample.mvvm.service.data.User

/**
 * Shared ViewModel to demonstrate passing data across Fragments.
 */
class SharedViewModel: ViewModel() {

    // shared [User]
    val sharedUser = MutableLiveData<User>()

}