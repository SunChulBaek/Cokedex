package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.common.getImageUrl
import kr.pe.ssun.cokedex.util.InfiniteGridHandler
import timber.log.Timber

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onClick: (Pokemon) -> Unit,
    onLoadMore: () -> Unit,
) = Column(
    modifier = modifier.background(Color(0xFF212121))
) {
    val pokemonList = (uiState as HomeUiState.Success).pokemonList
    val shortcuts = (uiState as HomeUiState.Success).shortcuts
    val gridState = rememberLazyGridState()

    // 개발용 UI
    LazyRow(
        modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.DarkGray),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(
            count = shortcuts.size,
            itemContent = { index ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onClick(
                            Pokemon(
                                id = shortcuts[index],
                                name = "테스트",
                                fallbackName = "테스트",
                                imageUrl = getImageUrl(shortcuts[index]),
                            )
                        )}
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.size(35.dp).align(Alignment.Center),
                        model = getImageUrl(shortcuts[index]),
                        contentDescription = null
                    )
                }
            }
        )
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth().weight(1f),
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
                    onClick = { colorStart, colorEnd ->
                        onClick(pokemonList[index].copy(
                            colorStart = colorStart,
                            colorEnd = colorEnd
                        ))
                    }
                )
            }
        )
    }
    InfiniteGridHandler(gridState = gridState) {
        Timber.d("[sunchulbaek] HomeContent.onLoadMore()")
        onLoadMore()
    }
}