package com.gil.sample.view.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.gil.sample.R
import com.gil.sample.viewmodel.FirstFragmentViewModel

// demo Composable for testing Rx and Cr functionality
@Composable
fun FirstScreen(viewModel: FirstFragmentViewModel) {

    val action1 = viewModel::onUpdateUsersKt2

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
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
    )
    {
        Text(text = text)
    }
}
