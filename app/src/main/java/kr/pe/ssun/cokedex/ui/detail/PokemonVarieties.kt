package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.model.PokemonDetail

@Composable
fun PokemonVarieties(
    modifier: Modifier = Modifier,
    varietyIds: List<Int>?,
    hostId: Int?,
    size: Dp = 60.dp,
    onClick: (Pokemon) -> Unit,
) = Column(modifier = modifier) {
    (0 until (((varietyIds?.size ?: 0) - 1) / 3) + 1).forEach { i ->
        Row {
            (0 until 3).forEach { j ->
                if (j > 0) {
                    Spacer(modifier = Modifier.weight(1f))
                }
                val index = (i * 3) + j
                if (index <= (varietyIds?.size ?: 0) - 1) {
                    PokemonThumb(
                        id = varietyIds?.get(index)!!,
                        hostId = hostId,
                        size = size,
                        normalColor = Color.White,
                        accentColor = Color(0xFF03a9f4),
                        isActive = { hostId == varietyIds[index] },
                        onClick = onClick,
                    )
                } else {
                    Spacer(modifier = Modifier.size(size))
                }
            }
        }
    }
}