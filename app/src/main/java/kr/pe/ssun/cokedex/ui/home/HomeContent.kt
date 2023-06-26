package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.theme.Green50

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: LazyPagingItems<Pokemon>,
    searchResult: List<Pokemon>,
    onSearch: (String) -> Unit,
    onClick: (Pokemon) -> Unit,
) = Column(
    modifier = modifier.background(Green50)
) {
    // 개발용 UI
//    HomeShortcuts(
//        modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.DarkGray),
//        shortcuts = HomeViewModel.SHORTCUTS,
//        onClick = onClick,
//    )

    // 검색
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newText ->
            text = newText
            onSearch(newText.text)
        },
        placeholder = { Text("Search") }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        // 전체 리스트
        val gridState = rememberLazyGridState()
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
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

        // 검색 결과
        val gridState2 = rememberLazyGridState()
        if (searchResult.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Green50),
                state = gridState2,
                columns = GridCells.Fixed(5),
                contentPadding = PaddingValues(8.dp), // 테두리
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(
                    count = searchResult.size,
                    key = { index -> searchResult[index]!!.id }
                ) { index ->
                    val item = searchResult[index]!!
                    PokemonGridItem(
                        item = item,
                        onClick = { onClick(item) }
                    )
                }
            }
        }
    }

    HomeLoadingProgress(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        uiState = uiState,
    )
}