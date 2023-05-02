package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val pokemon = (uiState as? PokemonUiState.Success)?.pokemon
    val colorStart = Color((uiState as? PokemonUiState.Success)?.colorStart ?: 0x00000000)
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorStart),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Cokedex")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(String.format("#%03d", pokemon?.id))
                    }
                }
            )
         },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        DefaultScreen(
            modifier = Modifier
                .background(Color(0xFF212121))
                .padding(innerPadding),
            isLoading = uiState is PokemonUiState.Loading,
            isError = uiState is PokemonUiState.Error,
        ) {
            PokemonDetailContent(uiState = uiState)
        }
    }
}