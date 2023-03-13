package gil.sample.mvvm.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import gil.sample.mvvm.viewmodel.SharedViewModel

/**
 * Sample base [Fragment].
 * Common functionality to go here // TODO
 * Holds shared view model for multi-fragment implementations.
 */
open class BaseFragment : Fragment() {

    // shared activity view model
    val sharedData by activityViewModels<SharedViewModel>()
}