package gil.sample.mvvm.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import gil.sample.mvvm.R
import gil.sample.mvvm.viewmodel.UsersRxViewModel

// demo Composable to display list of users
@Composable
fun UsersScreenRx(viewModel: UsersRxViewModel = viewModel()) {

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
                SimpleTextButton(
                    text = stringResource(id = R.string.update_users_rx_next),
                    onClick = { viewModel.onNext() }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    SimpleTextButton(
                        text = stringResource(id = R.string.update_users_rx1_label),
                        onClick = { viewModel.onUpdateUsersRx() }
                    )
                    SimpleTextButton(
                        text = stringResource(id = R.string.update_users_rx2_label),
                        onClick = { viewModel.onUpdateUsersRx2() }
                    )
                    SimpleTextButton(
                        text = stringResource(id = R.string.update_users_rx3_label),
                        onClick = { viewModel.onUpdateUsersRx3() }
                    )
                    SimpleTextButton(
                        text = stringResource(id = R.string.update_users_rx4_label),
                        onClick = { viewModel.onUpdateUsersRx4() },
                        modifier = Modifier.padding(bottom = 10.dp)
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