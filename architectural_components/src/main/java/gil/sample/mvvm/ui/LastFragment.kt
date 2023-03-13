package gil.sample.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import gil.sample.mvvm.theme.MyApplicationTheme
import gil.sample.mvvm.ui.widgets.LastScreen

class LastFragment : BaseFragment() {
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