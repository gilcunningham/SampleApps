package gil.sample.mvvm.view.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gil.sample.mvvm.R
import gil.sample.mvvm.service.data.User
import gil.sample.mvvm.viewmodel.UsersRxViewModel

/**
 * Simple vertical list view to display a list of data. //TODO- make generic
 *
 * @param listItems List of users as [LiveData].
 * @param headerText List view header string resource as [MutableState].
 * @param working [Boolean] to indicate background work being performed as [LiveData].
 */
@OptIn(ExperimentalUnitApi::class)
@Composable
fun SimpleListView(
    listItems: LiveData<List<User>>,  //TODO: make generic
    headerText: MutableState<Int> = mutableStateOf(R.string.simple_listview_empty_no_text),
    working: LiveData<Boolean> = MutableLiveData(false),
    onItemClick: (User) -> Unit = {}
) {


    // normalize values
    val header by remember { headerText }
    val list by listItems.observeAsState()
    val showProgress by working.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFA1A1A1)), // TODO: move to Color
            horizontalArrangement = Arrangement.Center

            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                //TODO : too much VM logic in a view
                text = if (showProgress == true) {
                    stringResource(header)
                } else {
                    stringResource(id = R.string.users_listview_header_label)
                },
                modifier = Modifier.padding(10.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = TextUnit(value = 18.0F, type = TextUnitType.Sp)
                ),
                fontWeight = FontWeight.Black
            )
        }
        // progress indicator
        SimpleListViewProgressIndicator(makingProgress = working)

        // list
        list?.let {
            LazyColumn {
                itemsIndexed(items = it) { index, user ->
                    SimpleListViewItem(
                        title = user.name,
                        modifier = Modifier
                            .clickable { onItemClick(user) }
                            .background(
                                if (index % 2 == 0) {
                                    Color(0xFFFFFFFF)
                                }
                                else {
                                    Color(0xFFE1E1E1) // TODO: move to Colors

                                }
                            )
                    )
                }
            }
            // empty list
        } ?: Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.simple_listview_empty_list_label))
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
    withDivider: Boolean = true,
    modifier: Modifier = Modifier,
    dividerModifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            title,
            modifier = Modifier.padding(15.dp)
        )
        if (withDivider) {
            Divider(
                modifier = dividerModifier.background(Color(0xFF909090))
            )
        }
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