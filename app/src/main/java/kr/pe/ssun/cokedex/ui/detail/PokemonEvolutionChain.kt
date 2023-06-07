package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.model.EvolutionChain
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.common.getItemImageUrl
import kr.pe.ssun.cokedex.util.asPx
import kr.pe.ssun.cokedex.util.asSp

@Composable
fun PokemonEvolutionChains(
    modifier: Modifier = Modifier,
    evolutionChain: EvolutionChain?,
    hostId: Int?,
    size: Dp = 60.dp,
    normalColor: Color = Color(0xFFbdbdbd),
    accentColor: Color = Color(0xFFc6ff00),
    onClick: (Pokemon) -> Unit,
) = Row(modifier = modifier) {
    val itemsByColumn = mutableListOf<List<Int>>()
    for (columnIndex in 0 until maxEvolutionChainLength(evolutionChain)) {
        // 진화 가지치기
        DrawEvolutionLines(columnIndex, evolutionChain, hostId, itemsByColumn, size, normalColor, accentColor)
        // 이미지
        DrawPokemons(columnIndex, evolutionChain, hostId, itemsByColumn, size, normalColor, accentColor, onClick)
    }
}

fun maxEvolutionChainLength(evolutionChain: EvolutionChain?) =
    evolutionChain?.chains?.size?.let { size ->
        when {
            size > 0 -> evolutionChain.chains.maxOf { it.size }
            else -> 0
        }
    } ?: 0

// 진화 가지치기
@Composable
private fun RowScope.DrawEvolutionLines(
    columnIndex: Int,
    evolutionChain: EvolutionChain?,
    hostId: Int?,
    itemsByColumn: List<List<Int>>,
    size: Dp,
    normalColor: Color,
    accentColor: Color,
) = if (columnIndex > 0) {
    val sizePx = size.asPx()
    Box(Modifier.weight(1f)) {
        columnPokemonIdTriggers(evolutionChain, columnIndex) { index, (id, trigger) ->
            val prevNodeIndex = prevNodeIndex(id, evolutionChain, itemsByColumn, columnIndex)
            Box(modifier = Modifier
                .padding(top = size * prevNodeIndex)
                .fillMaxWidth()
                .height(size * (index - prevNodeIndex + 1))
                .drawWithContent {
                    drawLine(
                        color = if (isActivePokemon(id, evolutionChain, hostId)) accentColor else normalColor,
                        strokeWidth = 10f,
                        start = Offset(0f, 0.5f * sizePx),
                        end = Offset(this.size.width, (index - prevNodeIndex + 0.5f) * sizePx)
                    )
                    drawContent()
                }
            ) {
                Box(modifier = Modifier
                    .align(Alignment.Center)
                    .size(15.dp)
                    .background(if (isActivePokemon(id, evolutionChain, hostId)) accentColor else normalColor)
                ) {
                    val levelUp: Int? = try { trigger.toInt() } catch (e: Throwable) { null }
                    if (levelUp != null) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Lv",
                            style = TextStyle(fontSize = 10.dp.asSp())
                        )
                    } else {
                        SubcomposeAsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = getItemImageUrl(trigger),
                            error = {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = "?",
                                        style = TextStyle(fontSize = 10.dp.asSp())
                                    )
                                }
                            },
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
} else Unit

@Composable
private fun DrawPokemons(
    columnIndex: Int,
    evolutionChain: EvolutionChain?,
    hostId: Int?,
    itemsByColumn: MutableList<List<Int>>,
    size: Dp,
    normalColor: Color,
    accentColor: Color,
    onClick: (Pokemon) -> Unit,
) = Column(modifier = Modifier.width(size)) {
    val items = columnPokemonIdTriggers(evolutionChain, columnIndex) { _, (id, trigger) ->
        PokemonThumb(
            id = id,
            hostId = hostId,
            size = size,
            normalColor = normalColor,
            accentColor = accentColor,
            isActive = { isActivePokemon(id, evolutionChain, hostId) },
            onClick = onClick,
        )
    }
    itemsByColumn.add(items.map { it.first })
}

fun isActivePokemon(id: Int, evolutionChain: EvolutionChain?, hostId: Int?) =
    activePokemonIds(
        evolutionChain = evolutionChain,
        hostId = hostId
    ).contains(id)

// Evolution Chain에서 현재 상세 페이지로 들어온 포켓몬 포함한 이전 포켓몬 id
// ex) 이상해풀(id=2)로 들어왔으면 [1, 2] (이상해꽃은 제외)
private fun activePokemonIds(evolutionChain: EvolutionChain?, hostId: Int?): List<Int> {
    val chain = evolutionChain?.chains?.firstOrNull { chain ->
        chain.map { it.first }.contains(hostId)
    }?.map { (id, _) -> id }

    return chain?.fold(listOf()) { acc, id ->
        if (chain.indexOf(id) <= chain.indexOf(hostId)) {
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
private fun columnPokemonIdTriggers(
    evolutionChain: EvolutionChain?,
    columnIndex: Int,
    action: @Composable (Int, Pair<Int, String>) -> Unit = { _, _ ->}
): List<Pair<Int, String>> {
    val list = (evolutionChain?.chains?.filter { chain ->
        chain.size > columnIndex
    }?.map { chain ->
        chain[columnIndex]
    }?.fold(listOf<Pair<Int, String>>()) { acc, (id, trigger) ->
        if (!acc.map { it.first }.contains(id)) {
            acc.plus(Pair(id, trigger))
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
    evolutionChain: EvolutionChain?,
    itemsByColumn: List<List<Int>>,
    columnIndex: Int
): Int {
    val prevNodeIndex = (evolutionChain?.chains?.firstOrNull { chain ->
        chain.map { (id, _) -> id }.contains(pId)
    }?.indexOfFirst { (id, _) -> id == pId } ?: 0) - 1

    val prevNodeId = evolutionChain?.chains?.firstOrNull { chain ->
        chain.map { (id, _) -> id }.contains(pId)
    }?.map { (id, _) -> id}?.get(prevNodeIndex)

    return itemsByColumn[columnIndex - 1].indexOfFirst { it == prevNodeId }
}