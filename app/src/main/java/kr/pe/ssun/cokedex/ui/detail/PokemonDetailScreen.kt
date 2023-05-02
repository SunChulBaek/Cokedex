package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.pe.ssun.cokedex.ui.common.DefaultScreen

@Composable
fun PokemonDetailRoute(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PokemonDetailScreen(
        uiState = uiState,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    uiState: PokemonUiState,
    onBack: () -> Unit
) {
    DefaultScreen(
        modifier = Modifier,
        isLoading = uiState is PokemonUiState.Loading,
        isError = uiState is PokemonUiState.Error,
        loading = { PokemonDetailContent(uiState = uiState, onBack = onBack) },
        backgroundColor = Color(0xFF212121)
    ) {
        PokemonDetailContent(uiState = uiState, onBack = onBack)
    }
}