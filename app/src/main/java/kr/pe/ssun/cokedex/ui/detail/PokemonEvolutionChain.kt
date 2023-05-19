package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.util.asPx

@Composable
fun PokemonEvolutionChains(
    modifier: Modifier = Modifier,
    pokemon: PokemonDetail?,
    size: Dp = 60.dp,
    normalColor: Color = Color(0xFFbdbdbd),
    accentColor: Color = Color(0xFFc6ff00),
    onClick: (Pokemon) -> Unit,
) = Row(modifier = modifier) {
    val itemsByColumn = mutableListOf<List<Int>>()
    for (columnIndex in 0 until maxEvolutionChainLength(pokemon)) {
        // 진화 가지치기
        DrawEvolutionLines(columnIndex, pokemon, itemsByColumn, size, normalColor, accentColor)
        // 이미지
        DrawPokemons(columnIndex, pokemon, itemsByColumn, size, normalColor, accentColor, onClick)
    }
}

fun maxEvolutionChainLength(pokemon: PokemonDetail?) =
    pokemon?.evolutionChains?.size?.let { size ->
        when {
            size > 0 -> pokemon.evolutionChains.maxOf { it.size }
            else -> 0
        }
    } ?: 0

// 진화 가지치기
@Composable
private fun RowScope.DrawEvolutionLines(
    columnIndex: Int,
    pokemon: PokemonDetail?,
    itemsByColumn: List<List<Int>>,
    size: Dp,
    normalColor: Color,
    accentColor: Color,
) = if (columnIndex > 0) {
    val sizePx = size.asPx()
    Box(Modifier.weight(1f)) {
        columnPokemonIds(pokemon, columnIndex) { index, id ->
            val prevNodeIndex = prevNodeIndex(id, pokemon, itemsByColumn, columnIndex)
            Box(modifier = Modifier
                .padding(top = size * prevNodeIndex)
                .fillMaxWidth()
                .height(size * (index - prevNodeIndex + 1))
                .drawWithContent {
                    drawLine(
                        color = if (isActivePokemon(id, pokemon)) accentColor else normalColor,
                        strokeWidth = 10f,
                        start = Offset(0f, 0.5f * sizePx),
                        end = Offset(this.size.width, (index - prevNodeIndex + 0.5f) * sizePx)
                    )
                    drawContent()
                }
            ) {
                // TODO : 진화 트리거 표시
            }
        }
    }
} else Unit

@Composable
private fun DrawPokemons(
    columnIndex: Int,
    pokemon: PokemonDetail?,
    itemsByColumn: MutableList<List<Int>>,
    size: Dp,
    normalColor: Color,
    accentColor: Color,
    onClick: (Pokemon) -> Unit,
) = Column(modifier = Modifier.width(size)) {
    val items = columnPokemonIds(pokemon, columnIndex) { _, id ->
        PokemonThumb(
            id = id,
            pokemon = pokemon,
            size = size,
            normalColor = normalColor,
            accentColor = accentColor,
            isActive = { isActivePokemon(id, pokemon) },
            onClick = onClick,
        )
    }
    itemsByColumn.add(items)
}

fun isActivePokemon(id: Int, pokemon: PokemonDetail?) =
    activePokemonIds(pokemon).contains(id)

// Evolution Chain에서 현재 상세 페이지로 들어온 포켓몬 포함한 이전 포켓몬 id
// ex) 이상해풀(id=2)로 들어왔으면 [1, 2] (이상해꽃은 제외)
private fun activePokemonIds(pokemon: PokemonDetail?): List<Int> {
    val chain = pokemon?.evolutionChains?.firstOrNull { chain ->
        chain.map { it.first }.contains(pokemon.id)
    }?.map { (id, _) -> id }

    return chain?.fold(listOf()) { acc, id ->
        if (chain.indexOf(id) <= chain.indexOf(pokemon.id)) {
            acc.plus(id)
        } else {
            acc
        }
    } ?: listOf()
}

// columnIndex에 해당하는 포켓몬의 id
// ex) 개무소(265) - 실쿤(266)  - 뷰티플라이(267)
//               - 카스쿤(268) - 독케일(269)
// columnIndex = 1 일 때 [266, 268]
@Composable
private fun columnPokemonIds(
    pokemon: PokemonDetail?,
    columnIndex: Int,
    action: @Composable (Int, Int) -> Unit = { _, _ ->}
): List<Int> {
    val list = (pokemon?.evolutionChains?.filter { chain ->
        chain.size > columnIndex
    }?.map { chain ->
        chain[columnIndex]
    }?.fold(listOf<Int>()) { acc, (id, _) ->
        if (!acc.contains(id)) {
            acc.plus(id)
        } else {
            acc
        }
    } ?: listOf())
    list.forEachIndexed { index, id ->
        action(index, id)
    }
    return list
}

// 이전 포켓몬의 인덱스
// ex) 개무소(265) - 실쿤(266)  - 뷰티플라이(267)
//               - 카스쿤(268) - 독케일(269)
// 독케일(269)의 경우 이전 칼럼 [266, 268]에서 카스쿤(268)의 인덱스 = 1
private fun prevNodeIndex(
    pId: Int,
    pokemon: PokemonDetail?,
    itemsByColumn: List<List<Int>>,
    columnIndex: Int
): Int {
    val prevNodeIndex = (pokemon?.evolutionChains?.firstOrNull { chain ->
        chain.map { (id, _) -> id }.contains(pId)
    }?.indexOfFirst { (id, _) -> id == pId } ?: 0) - 1
    val prevNodeId = pokemon?.evolutionChains?.firstOrNull { chain ->
        chain.map { (id, _) -> id }.contains(pId)
    }?.map { (id, _) -> id}?.get(prevNodeIndex)

    return itemsByColumn[columnIndex - 1].indexOfFirst { it == prevNodeId }
}