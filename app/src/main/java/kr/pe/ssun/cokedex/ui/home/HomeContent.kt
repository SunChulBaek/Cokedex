package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.data.model.Pokemon
import kr.pe.ssun.cokedex.util.InfiniteGridHandler
import timber.log.Timber

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onClick: (Pokemon) -> Unit,
    onLoadMore: (Int) -> Unit,
) {
    val pokemonList = (uiState as HomeUiState.Success).pokemonList
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp), // 테두리
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = pokemonList.size,
            itemContent = { index ->
                PokemonGridItem(
                    item = pokemonList[index],
                    onClick = { onClick(pokemonList[index]) }
                )
            }
        )
    }
    InfiniteGridHandler(gridState = gridState) {
        onLoadMore(pokemonList.size)
    }
}