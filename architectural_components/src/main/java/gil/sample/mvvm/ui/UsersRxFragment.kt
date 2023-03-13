package gil.sample.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import gil.sample.mvvm.R
import gil.sample.mvvm.theme.MyApplicationTheme
import gil.sample.mvvm.ui.widgets.UsersScreenRx
import gil.sample.mvvm.viewmodel.UsersRxViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A sample [Fragment] to display a list of [User]s and is the default destination
 * in the navigation.
 */
class UsersRxFragment : BaseFragment() {

    override val mViewModel: UsersRxViewModel by viewModels()

    //val viewModel: UsersRxViewModel by viewModels {
    //    Injector.provideUsersRxViewModelFactory(requireContext())
    //}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyApplicationTheme {
                    UsersScreenRx()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // For testing and demo purposes only, can be removed
        mViewModel.users.observe(viewLifecycleOwner) { users ->
            users.forEach { user ->
                Timber.d("user --> ${user.name} ${user.email}")
            }
        }

        // For testing and demo purposes only, can be removed
        mViewModel.doingWork.observe(viewLifecycleOwner) { working ->
            Timber.d("doingWork --> $working")
        }

        // user selected
        lifecycleScope.launch {
            mViewModel.onUserSelected.collect { user ->
                Timber.d("onUserSelected - $user")
                // sh
                sharedData.sharedUser.value = user
                //TODO: shared vm, load next users
            }
        }
    }

    override fun getNextNavigation() : Int {
        return R.id.action_usersRxFragment_next
    }
}