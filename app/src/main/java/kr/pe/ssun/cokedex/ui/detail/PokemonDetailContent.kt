package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.common.CancelButton

@Composable
fun PokemonDetailContent(
    uiState: PokemonUiState,
    onClick: (Pokemon) -> Unit,
    onBack: () -> Unit
) {
    val success = uiState as? PokemonUiState.Success
    val loading = uiState as? PokemonUiState.Loading

    val pokemon = success?.pokemon // 프로그레스 표시용

    val items = success?.items ?: listOf(
        PokemonDetailImage(id = loading?.id ?: 0),
        PokemonDetailName(
            id = loading?.id ?: 0,
            name = loading?.name ?: "",
        )
    )
    PokemonDetailBg(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(
                    count = items.size,
                    itemContent = { index ->
                        items[index].ItemContent(onClick)
                    }
                )
            }
            // 프로그레스
            PokemonDetailLoadingProgress(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                pokemon = pokemon
            )
        }
        CancelButton(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 15.dp),
            onClick = { onBack() }
        )
    }
}