package kr.pe.ssun.cokedex.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isError: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    loading: @Composable () -> Unit = { LoadingScreen(backgroundColor) },
    error: @Composable () -> Unit = { ErrorScreen(backgroundColor) },
    content: @Composable () -> Unit,
) = Box(modifier = modifier) {
    when {
        isLoading -> { loading() }
        isError -> { error() }
        else -> content()
    }
}