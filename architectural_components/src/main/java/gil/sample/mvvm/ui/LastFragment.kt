package gil.sample.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import gil.sample.mvvm.theme.MyApplicationTheme
import gil.sample.mvvm.ui.widgets.LastScreen
import gil.sample.mvvm.viewmodel.UsersRxViewModel

class LastFragment : BaseFragment() {

    override val mViewModel: UsersRxViewModel by viewModels()

    override fun getNextNavigation(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MyApplicationTheme {
                    LastScreen(sharedData)
                }
            }
        }
    }
}