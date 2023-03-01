package com.gil.sample.view.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.gil.sample.R
import com.gil.sample.theme.MyApplicationTheme
import com.gil.sample.viewmodel.UsersCrViewModel
import com.gil.sample.viewmodel.UsersRxViewModel

// demo Composable to display user response
@OptIn(ExperimentalUnitApi::class)
@Composable
fun UsersScreenRx(viewModel: UsersRxViewModel) {

    val loading by viewModel.doingWork.observeAsState()

    MyApplicationTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = Color(0xFFF0F0F0)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .background(Color(0xFF00FF00))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    SampleTextButton(
                        text = stringResource(id = R.string.update_users_rx_next),
                        onClick = { viewModel.onNext() }
                    )
                }

                Row(
                    modifier = Modifier
                        .background(Color(0xFF0000FF))
                        .fillMaxWidth(),
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
                    }
                }

                SimpleListView(
                    listItems = viewModel.usersRx,
                    headerText = viewModel.listHeader,
                    working = viewModel.doingWork
                )
            }
        }
    }
}

// demo Composable for testing Cr functionality
@Composable
fun UsersScreenCr(viewModel: UsersCrViewModel) {

    MyApplicationTheme {
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
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SampleUserListView2(
    viewModel: UsersRxViewModel
) {
    val users by viewModel.usersRx.observeAsState()
    val working by viewModel.doingWork.observeAsState()
    val header by viewModel.listHeader

    val header2 = working?.let {
        if (working == true) { viewModel.listHeader } else { "Users" }
    } ?: viewModel.listHeader

    /**
    remember {
        mutableStateOf<String>(
            if (working == true) { "Updating" } else { "Users" }

        )
    }
    **/

    Column(
        // inside this column we are specifying modifier
        // to specify max width and max height
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        // on below line we are specifying horizontal alignment
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are creating a simple text
        // view for displaying heading for our application
        //headerText?.let {
        Text(
            text = if (working == true) { header } else { "Users" },
            modifier = Modifier.padding(10.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = TextUnit(value = 18.0F, type = TextUnitType.Sp)
            ),
            fontWeight = FontWeight.Black
        )
        //}

        /**
        working?.let {
            AnimatedVisibility(visible = it) {
                CircularProgressIndicatorSample(doingWork = viewModel.doingWork)
            }
        }
        **/

        users?.let {
            LazyColumn {
                items(it) { user ->
                    Text(user.name, modifier = Modifier.padding(15.dp))

                    Divider()
                }
            }
            //?: Text("- Empty - ", modifier = Modifier.padding(15.dp))
        } ?: Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF00FF00)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("- Empty -")
        }
    }
}


@Composable
fun SampleTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
    )
    {
        Text(text = text)
    }
}

@Composable
fun CircularProgressIndicatorSample() {
    //val loading = doingWork.observeAsState()
    var progress by remember { mutableStateOf(0.1f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Spacer(Modifier.height(30.dp))
        //Text("CircularProgressIndicator with undefined progress")
        CircularProgressIndicator()
        //Spacer(Modifier.height(30.dp))
        //Text("CircularProgressIndicator with progress set by buttons")
        //CircularProgressIndicator(progress = animatedProgress)
        //Spacer(Modifier.height(30.dp))
        /**
        OutlinedButton(
        onClick = {
        if (progress < 1f) progress += 0.1f
        }
        ) {
        Text("Increase")
        }

        OutlinedButton(
        onClick = {
        if (progress > 0f) progress -= 0.1f
        }
        ) {
        Text("Decrease")
        }
         **/
    }


    //loading.value?.let {
    //    AnimatedVisibility(visible = true) {
    //        Column(horizontalAlignment = Alignment.CenterHorizontally) {
    //            CircularProgressIndicator()
    //        }
    //    }
    //}

    //loading?.let {
    //    if (loading.value == true) {
    //
    //    }
    //}
}

