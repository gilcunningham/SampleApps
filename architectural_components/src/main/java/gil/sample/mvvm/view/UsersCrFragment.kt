package gil.sample.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import gil.sample.mvvm.theme.MyApplicationTheme
import gil.sample.mvvm.view.widgets.UsersScreenCr
import gil.sample.mvvm.viewmodel.UsersCrViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UsersCrFragment : Fragment() {

    val LOG_TAG = UsersCrFragment::class.java.simpleName

    val mViewModel: UsersCrViewModel by viewModels()

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
            Timber.tag(LOG_TAG).d("--- users cr updates ---")
            users.forEach { user ->
                Timber.d("user --> ${user.name} ${user.email}")
            }
        }

        // for testing and demo purposes only, not needed for functionality
        lifecycleScope.launch() {
            mViewModel.usersFlow.collect { users ->
                Timber.tag(LOG_TAG).d("--- users cr flow updates ---")
                users.forEach { user ->
                    Timber.d("user --> ${user.name} ${user.email}")
                }
            }
        }
    }
}