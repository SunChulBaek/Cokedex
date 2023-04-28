package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.ui.common.DefaultScreen

@Composable
fun PokemonDetailRoute(
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
    val pokemon = (uiState as PokemonUiState.Success).pokemon

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val imageRef = createRef()
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
        }
        Text(
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row {
            Spacer(modifier = Modifier.weight(1f))
            pokemon.types.forEach {type ->
                Text(text = type.name)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}