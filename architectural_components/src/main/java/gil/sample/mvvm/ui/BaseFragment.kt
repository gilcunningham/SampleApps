package gil.sample.mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import gil.sample.mvvm.viewmodel.BaseUsersViewModel
import gil.sample.mvvm.viewmodel.SharedViewModel
import timber.log.Timber

/**
 * Sample base [Fragment] with common functionality and shared view model.
 *
 * Note | Learning :
 * As this app supports back and "next" navigation, we can assume that this Fragment will
 * be visited multiple times. We need a method to cleanup the previous View from the
 * ViewModel's observables.
 *
 * Here are a few options:
 *
 * Option 1:
 * LiveData observe(this) in onCreate()
 * Observing in onCreate() will survive detached Views and safe upon return and new attached View.
 * LiveData will not "re-emit" upon onResume() as the previous View was detached and no longer observing.
 *
 * Option 2:
 * Flow collect() in onCreate()
 * Observing in onCreate() will survive detached Views and safe upon return and new attached View.
 * This method requires collect() within a new CoroutineScope.
 *
 * Option 3:
 * LiveData observe(lifecycleOwner) in onViewCreated().
 * Navigating to next and then back to this Fragment will cause LiveData to emit (existing) state.
 * Without some work to "toggle" LiveData state navigation back to this Fragment will cause
 * LiveData to emit again.
 *
 * Option 4:
 * Flow collect() in onViewCreated().
 * Navigating to next and then back to this Fragment will not cause Flow to emit (existing) state,
 * only new state. Because collect() is called inside the View's lifecycle scope, previous View's
 * are still "collecting" Flow emits. Upon next navigation, a previous View attempts to navigate to
 * next and will fail with "Navigation action/destination ... <id> ... cannot be found from the current destination".
 * A clean solution is to maintain a reference to the existing Job returned by lifecycleScope.launch().
 * The Job should be cancelled when the View is destroyed.
 *
 * Note: Various Options are left commented out.
 */
abstract class BaseFragment : Fragment() {

    // shared activity view model
    val sharedData: SharedViewModel by activityViewModels()

    abstract val mViewModel: BaseUsersViewModel

    // handle to next navigation job
    // Option 4
    // private lateinit var onNextJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle back navigation
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val navigatedUp = findNavController().navigateUp()
                    // last fragment
                    if (!navigatedUp) {
                        activity?.finish()
                    }
                }
            })


        // Option 1
        mViewModel.onNext.observe(this) { next ->
            Timber.d("onNext - $next")
            if (next) {
                findNavController().navigate(getNextNavigation())
            }
        }

        /**
         * Option 2
        CoroutineScope(Dispatchers.Main).launch {
            mViewModel.onNext.collect { next ->
                Timber.d("onNext - $next")
                if (next) {
                    findNavController().navigate(getNextNavigation())
                }
            }
        }
        **/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Option 3
        mViewModel.onNext.observe(viewLifecycleOwner) { next ->
            Timber.d("onNext - $next")
            if (next) {
                findNavController().navigate(getNextNavigation())
            }
        }
        **/

        /** Option 4
        // get handle to "on next" Job so View can later be decoupled from observable
        onNextJob = lifecycleScope.launch {
            mViewModel.onNext.collect { next ->
                Timber.d("onNext - $next")
                if (next) {
                    findNavController().navigate(getNextNavigation())
                }
            }
        }
        **/
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Option 4
        // Cancelling the Job will decouple the observers from the View
        //onNextJob.cancel()
    }

    // next navigation resource ID
    protected abstract fun getNextNavigation() : Int
}