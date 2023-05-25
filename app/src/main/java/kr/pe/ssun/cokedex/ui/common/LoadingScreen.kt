package kr.pe.ssun.cokedex.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(backgroundColor: Color = MaterialTheme.colorScheme.background) {
    Box(modifier = Modifier.background(backgroundColor).fillMaxSize()) {
        PokemonProgressIndicator(
            modifier = Modifier.align(Alignment.Center).size(200.dp),
        )
    }
}