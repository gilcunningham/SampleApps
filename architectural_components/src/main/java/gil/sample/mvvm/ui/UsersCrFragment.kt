package gil.sample.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import gil.sample.mvvm.R
import gil.sample.mvvm.theme.MyApplicationTheme
import gil.sample.mvvm.ui.widgets.UsersScreenCr
import gil.sample.mvvm.viewmodel.UsersCrViewModel
import gil.sample.mvvm.viewmodel.UsersRxViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A sample [Fragment] to display a list of [User]s.
 */
@AndroidEntryPoint
class UsersCrFragment : BaseFragment() {

    override val mViewModel: UsersCrViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    println("*** handle back")
                    findNavController().navigateUp()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyApplicationTheme {
                    UsersScreenCr(mViewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // for testing and demo purposes only, not needed for functionality
        mViewModel.users.observe(viewLifecycleOwner) { users ->
            Timber.d("--- users cr updates ---")
            users.forEach { user ->
                Timber.d("user --> ${user.name} ${user.email}")
            }
        }

        // for testing and demo purposes only, not needed for functionality
        lifecycleScope.launch() {
            mViewModel.usersFlow.collect { users ->
                Timber.d("--- users cr flow updates ---")
                users.forEach { user ->
                    Timber.d("user --> ${user.name} ${user.email}")
                }
            }
        }
    }

    override fun getNextNavigation(): Int {
        return R.id.action_usersCrFragment_next
    }
}