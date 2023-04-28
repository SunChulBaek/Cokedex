package kr.pe.ssun.cokedex.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isError: Boolean = false,
    content: @Composable () -> Unit,
) = Box(modifier = modifier) {
    when {
        isLoading -> LoadingScreen()
        isError -> ErrorScreen()
        else -> content()
    }
}