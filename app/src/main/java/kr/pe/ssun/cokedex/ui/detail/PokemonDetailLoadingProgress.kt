package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.model.Loadable
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.model.findById

@Composable
fun PokemonDetailLoadingProgress(
    modifier: Modifier = Modifier,
    pokemon: PokemonDetail?
) = Column(
    modifier = modifier.background(Color(0xFFe0e0e0)),
) {
    // Type 로딩 상태를 표시함
    val totalTypeIds = pokemon?.totalTypeIds

    listOf(
        Pair(pokemon?.types, totalTypeIds),
    ).forEach { (items, ids) ->
        LoadingProgress(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            items = items,
            totalId = ids,
        )
    }
}

@Composable
private fun LoadingProgress(
    modifier: Modifier = Modifier,
    items: List<Loadable>?,
    totalId: List<Int>?,
    colorFromInternet: Color = Color(0xFF4caf50),
    colorFromDB: Color = Color(0xFF3f51b5),
) = Row(modifier = modifier) {
    totalId?.forEach { id ->
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    when (items?.findById(id)?.fromDB) {
                        true -> colorFromDB
                        false -> colorFromInternet
                        else -> Color.Transparent
                    }
                )
                .border(0.3.dp, Color.Black)
        )
    }
}