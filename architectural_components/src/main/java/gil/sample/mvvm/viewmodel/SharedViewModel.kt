package gil.sample.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gil.sample.mvvm.data.model.User

/**
 * Shared ViewModel to demonstrate sharing data across multiple Fragments outside the scope
 * of passed Fragment to Fragment data or Fragment to member ViewModel.
 */
class SharedViewModel: ViewModel() {

    // shared [User]
    val sharedUser = MutableLiveData<User>()

}