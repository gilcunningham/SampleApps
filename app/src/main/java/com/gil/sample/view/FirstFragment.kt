package com.gil.sample.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gil.sample.view.widgets.FirstScreen
import com.gil.sample.viewmodel.FirstFragmentViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment() {

    val LOG_TAG = FirstFragment::class.java.simpleName

    val mViewModel: FirstFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FirstScreen(mViewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO - add these to a listview
        // on demand
        mViewModel.usersRx.observe(viewLifecycleOwner) { users ->
            Timber.tag(LOG_TAG).d("--- users rx updates ---")
            users.forEach { user ->
                Timber.d("user --> ${user.name} ${user.email}")
                Timber.tag(LOG_TAG).d("user --> ${user.name} ${user.email}")
            }
        }

        // observe livedata from con demand
        mViewModel.usersCr.observe(viewLifecycleOwner) { users ->
            Timber.tag(LOG_TAG).d("--- users cr updates ---")
            users.forEach { user ->
                Timber.d("user --> ${user.name} ${user.email}")
                Timber.tag(LOG_TAG).d("user --> ${user.name} ${user.email}")
            }
        }

        // observe flow
        lifecycleScope.launch {
            System.out.println("*** lifecycleScope.launch")
            mViewModel.usersFlow.collect { users ->
                users.forEach { user ->
                    Timber.tag(LOG_TAG).d("user --> ${user.name} ${user.email}")
                }
            }
        }
    }
}