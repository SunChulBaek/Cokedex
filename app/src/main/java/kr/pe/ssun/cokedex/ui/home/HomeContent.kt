package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.theme.DeepPurple50

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: LazyPagingItems<Pokemon>,
    onClick: (Pokemon) -> Unit,
) = Column(
    modifier = modifier.background(DeepPurple50)
) {
    // 개발용 UI
    HomeShortcuts(
        modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.DarkGray),
        shortcuts = HomeViewModel.SHORTCUTS,
        onClick = onClick,
    )

    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        state = gridState,
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(8.dp), // 테두리
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(
            count = uiState.itemCount,
            key = { index -> uiState[index]!!.id }
        ) { index ->
            val item = uiState[index]!!
            PokemonGridItem(
                item = item,
                onClick = { onClick(item) }
            )
        }
    }
}