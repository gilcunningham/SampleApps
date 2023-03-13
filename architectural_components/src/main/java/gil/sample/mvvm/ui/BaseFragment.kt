package gil.sample.mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import gil.sample.mvvm.viewmodel.BaseUsersViewModel
import gil.sample.mvvm.viewmodel.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Sample base [Fragment].
 * Common functionality to go here // TODO
 * Holds shared view model for multi-fragment implementations.
 */
abstract class BaseFragment : Fragment() {

    // shared activity view model
    val sharedData: SharedViewModel by activityViewModels()

    abstract val mViewModel: BaseUsersViewModel

    // handle to next navigation job
    // Note:
    // As this app supports back and "next" navigation, we can assume that this Fragment will
    // be visited multiple times. We need a method to cleanup the previous View from the
    // ViewModel's observables.
    private lateinit var onNextJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: mess around with navigation in fragment
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
        }

        mViewModel.onNext.asLiveData().observe(this) { next ->
            println("test")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get handle to "on next" Job so View can later be decoupled from observable
        onNextJob = lifecycleScope.launch {
            mViewModel.onNext.collect { next ->
                Timber.d("onNext - $next")
                if (next) {
                    findNavController().navigate(getNextNavigation())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // remove this View from onNext observable
        // Note | Learning:
        // TODO:
        // without decoupling the Fragment from the observable, revisiting (new View) this
        // Fragment will result in multiple observers of viewModel.onNext. Only the current
        // View is valid and can perform navigation (in this use case).
        // Without removing the observer at the end of the lifecycle, the result will be :
        // Navigation action/destination <id> cannot be found from the current destination
        //
        // and LiveData equivalent
        // as LiveData
        //mViewModel.onNext.removeObserver(observer)

        onNextJob.cancel()
    }

    // next navigation resource ID
    protected abstract fun getNextNavigation() : Int
}