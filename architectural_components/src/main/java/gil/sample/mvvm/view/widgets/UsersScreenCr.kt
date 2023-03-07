package gil.sample.mvvm.view.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gil.sample.mvvm.R
import gil.sample.mvvm.viewmodel.UsersCrViewModel

// demo Composable for testing Cr functionality
@Composable
fun UsersScreenCr(viewModel: UsersCrViewModel) {

    println("*** UserScreenCr - ${viewModel}")

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
                horizontalArrangement = Arrangement.Start
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
                        text = stringResource(id = R.string.update_users_cr1_label),
                        onClick = { viewModel.onUpdateUsersCr() }
                    )
                    SimpleTextButton(
                        text = stringResource(id = R.string.update_users_cr2_label),
                        onClick = { viewModel.onUpdateUsersCr2() }
                    )
                    SimpleTextButton(
                        text = stringResource(id = R.string.update_users_cr3_label),
                        onClick = { viewModel.onUpdateUsersCr3() },
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
