package gil.sample.mvvm.view.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import gil.sample.mvvm.viewmodel.SharedViewModel

@Composable
fun LastScreen(sharedData: SharedViewModel) {

    val foo: SharedViewModel by hiltViewModel()

    val user by sharedData.sharedUser.observeAsState()

    Surface(
        Modifier.fillMaxSize(),
        color = Color(0xFFF0F0F0) // TODO: move Color
    ) {
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            user?.let {
                Row {
                    Text(text = "Selected User on first screen:")
                }
                Row {
                    Text(text = "Name: ${it.name}")
                }
                Row {
                    Text(text = "Email: ${it.email}")
                }
                Row {
                    Text(text = "Status: ${it.status}")
                }
                Row {
                    Text(text = "Id: ${it.id}")
                }
            } ?: Row {
                Text(text = "No User selected on first screen")
            }
        }
    }
}