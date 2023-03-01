package com.gil.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gil.sample.view.widgets.UsersScreenCr
import com.gil.sample.viewmodel.UsersCrViewModel
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
                UsersScreenCr(mViewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // for testing and demo purposes only, not needed for functionality
        mViewModel.users.observe(viewLifecycleOwner) { users ->
            Timber.tag(LOG_TAG).d("--- users rx updates ---")
            users.forEach { user ->
                Timber.d("user --> ${user.name} ${user.email}")
                Timber.tag(LOG_TAG).d("user --> ${user.name} ${user.email}")
            }
        }
    }
}