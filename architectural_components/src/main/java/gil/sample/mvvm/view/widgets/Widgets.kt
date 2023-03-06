package gil.sample.mvvm.view.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import gil.sample.mvvm.R
import gil.sample.mvvm.viewmodel.UsersCrViewModel
import gil.sample.mvvm.viewmodel.UsersRxViewModel
import kotlinx.coroutines.flow.SharedFlow

// demo Composable to display list of users
@Composable
fun UsersScreenRx(viewModel: UsersRxViewModel) {

    // Toast displays selected user
    SimpleToast(
        data = viewModel.onUserSelected
    )

    Surface(
        Modifier.fillMaxSize(),
        color = Color(0xFFF0F0F0)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                SampleTextButton(
                    text = stringResource(id = R.string.update_users_rx_next),
                    onClick = { viewModel.onNext() }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    SampleTextButton(
                        text = stringResource(id = R.string.update_users_rx1_label),
                        onClick = { viewModel.onUpdateUsersRx() }
                    )
                    SampleTextButton(
                        text = stringResource(id = R.string.update_users_rx2_label),
                        onClick = { viewModel.onUpdateUsersRx2() }
                    )
                    SampleTextButton(
                        text = stringResource(id = R.string.update_users_rx3_label),
                        onClick = { viewModel.onUpdateUsersRx3() }
                    )
                    SampleTextButton(
                        text = stringResource(id = R.string.update_users_rx4_label),
                        onClick = { viewModel.onUpdateUsersRx4() }
                    )
                }
            }

            SimpleListView(
                listItems = viewModel.users,
                headerText = viewModel.listHeader,
                working = viewModel.doingWork,
                onItemClick = viewModel::onUserSelected
            )
        }
    }
}

// Toast composable
@Composable
fun <T> SimpleToast(
    withContext: Context = LocalContext.current,
    data: SharedFlow<T>,
    extra: String? = null,
    duration: Int = Toast.LENGTH_SHORT
) {
    LaunchedEffect(Unit) {
        data.collect {
            Toast.makeText(
                withContext,
                "${it.toString()} $extra",
                duration
            ).show()
        }
    }
}

// demo Composable for testing Cr functionality
@Composable
fun UsersScreenCr(viewModel: UsersCrViewModel) {

    Surface(
        Modifier.fillMaxSize(),
        color = Color(0xFFF0F0F0)
    ) {
        Box {
            Column(
                Modifier.align(
                    Alignment.TopCenter
                )
            )
            {
                SampleTextButton(
                    text = stringResource(id = R.string.update_users_cr1_label),
                    onClick = { viewModel.onUpdateUsersKt() }
                )
                SampleTextButton(
                    text = stringResource(id = R.string.update_users_cr2_label),
                    onClick = { viewModel.onUpdateUsersKt2() }
                )
                SampleTextButton(
                    text = stringResource(id = R.string.update_users_cr3_label),
                    onClick = { viewModel.onUpdateUsersKt3() }
                )
            }
        }
    }
}

@Composable
fun SampleTextButton(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
    )
    {
        Text(
            text = text,
            modifier = textModifier
        )
    }
}

//@Composable


