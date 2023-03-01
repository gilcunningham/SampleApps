package com.gil.sample.view.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gil.sample.service.data.User

/**
 * Simple list view to display a list of data. //TODO- make generic
 *
 * @param listItems [LiveData] holding [List] of [User]s.
 * @param headerText [MutableState] holding [String] list header text.
 * @param working [LiveData] holding [Boolean] to indicate background work being performed.
 */
@OptIn(ExperimentalUnitApi::class)
@Composable
fun SimpleListView(
    listItems: LiveData<List<User>>,  //TODO: make generic
    headerText: MutableState<String>? = null,
    working: LiveData<Boolean>? = null
) {
    // normalize values
    val header by remember { headerText ?: mutableStateOf("") }
    val list by listItems.observeAsState()
    val showProgress by working?.observeAsState() ?: MutableLiveData(false).observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // header
        Text(
            //TODO : too much logic in a view
            text = if (showProgress == true) {
                header
            } else {
                "Users"
            },
            modifier = Modifier.padding(10.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = TextUnit(value = 18.0F, type = TextUnitType.Sp)
            ),
            fontWeight = FontWeight.Black
        )
        // progress indicator
        SimpleListViewProgressIndicator(makingProgress = working)

        // list
        list?.let {
            LazyColumn {
                items(it) { user ->
                    SimpleListViewItem(title = user.name)
                }
            }
        // empty list
        } ?: Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("- Empty -") //TODO string res
        }
    }
}

/**
 * Simple list view item to display a single [Text].
 * @param title [String] title text.
 * @param withDivider [Boolean] to show a divider. If false, not divider will be shown.
 */
@Composable
private fun SimpleListViewItem(
    title: String,
    withDivider: Boolean = true
) {
    Text(
        title,
        modifier = Modifier.padding(15.dp)
    )
    if (withDivider) {
        Divider()
    }
}

/**
 * Simple list view circular progress indicator.
 *
 * @param makingProgress [LiveData] with [Boolean] to indicate background work in progress.
 * @param modifier [Modifier] to be applied to progress indicator's layout.
 * By default, the progress indicator will appear centered in its layout.
 */
@Composable
fun SimpleListViewProgressIndicator(
    makingProgress: LiveData<Boolean>? = null,
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val visible by makingProgress?.observeAsState() ?: MutableLiveData(false).observeAsState()
    visible?.let {
        AnimatedVisibility(visible = it) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}