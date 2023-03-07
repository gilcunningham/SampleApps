package gil.sample.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import gil.sample.mvvm.R
import gil.sample.mvvm.theme.MyApplicationTheme
import gil.sample.mvvm.view.widgets.UsersScreenRx
import gil.sample.mvvm.viewmodel.BaseFragmentViewModelFactory
import gil.sample.mvvm.viewmodel.UsersRxViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A sample [Fragment] to display a list of [User]s and is the default destination
 * in the navigation.
 */
class UsersRxFragment : BaseFragment() {

    val mViewModel: UsersRxViewModel by viewModels()

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
                    UsersScreenRx(mViewModel)
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

        // navigate to next fragment
        mViewModel.onNext.observe(viewLifecycleOwner) { next ->
            Timber.d("onNext - $next")
            if (next) {
                findNavController().navigate(R.id.action_userRxFragment_to_usersCrFragment)
            }
        }

        // user selected
        lifecycleScope.launch {
            mViewModel.onUserSelected.collect { user ->
                Timber.d("onUserSelected - $user")
                //TODO: shared vm, load next users
            }
        }
    }
}