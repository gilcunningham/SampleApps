package com.gil.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gil.sample.R
import com.gil.sample.view.widgets.UsersScreenRx
import com.gil.sample.viewmodel.BaseFragmentViewModelFactory
import com.gil.sample.viewmodel.UsersRxViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UsersRxFragment : BaseFragment() {

    val mViewModel: UsersRxViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                UsersScreenRx(mViewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // No need to observe this here.
        // For testing and demo purposes only
        mViewModel.usersRx.observe(viewLifecycleOwner) { users ->
            users.forEach { user ->
                Timber.d("user --> ${user.name} ${user.email}")
            }
        }

        //
        mViewModel.onNext.observe(viewLifecycleOwner) {
            Timber.d("onNext")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        mViewModel.doingWork.observe(viewLifecycleOwner) { working ->
            Timber.d("doingWork --> $working")
        }
    }
}