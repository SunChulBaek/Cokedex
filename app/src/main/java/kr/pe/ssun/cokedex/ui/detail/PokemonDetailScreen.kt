package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.ui.common.DefaultScreen

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DefaultScreen(
        isLoading = uiState is PokemonUiState.Loading,
        isError = uiState is PokemonUiState.Error,
    ) {
        PokemonDetailScreen(
            uiState = uiState,
        )
    }
}

@Composable
fun PokemonDetailScreen(
    uiState: PokemonUiState,
) {
    val pokemon = (uiState as PokemonUiState.Success)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (imageRef, nameRef) = createRefs()
        SubcomposeAsyncImage(
            modifier = Modifier.constrainAs(imageRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.ratio("1:1")
            },
            model = pokemon.imageUrl,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.padding(25.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            contentDescription = "thumbnail"
        )
        Text(
            modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(imageRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}