package gil.sample.mvvm.view.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow

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


// Simple text button reusable
@Composable
fun SimpleTextButton(
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

