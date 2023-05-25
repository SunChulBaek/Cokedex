package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.ui.common.PokemonProgressIndicator
import kr.pe.ssun.cokedex.ui.common.getImageUrl
import kr.pe.ssun.cokedex.util.MyPalette
import kr.pe.ssun.cokedex.util.asSp

@Composable
fun PokemonThumb(
    id: Int,
    pokemon: PokemonDetail?,
    size: Dp,
    normalColor: Color,
    accentColor: Color,
    isActive: () -> Boolean = { false },
    onClick: (Pokemon) -> Unit = { },
) {
    var colorStart by remember { mutableStateOf(Color.Transparent.toArgb()) }
    var colorEnd by remember { mutableStateOf(Color.Transparent.toArgb()) }

    Box(modifier = Modifier
        .size(size)
        .clip(CircleShape)
        .background(if (isActive()) accentColor else normalColor)
        .run {
            if (pokemon?.id != null && pokemon.id != id) {
                clickable { onClick(Pokemon(
                    id = id,
                    name = "name",
                    fallbackName = "name",
                    imageUrl = getImageUrl(id),
                    colorStart = colorStart,
                    colorEnd = colorEnd
                )) }
            } else this
        }
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 5.dp)
                .size(size - 20.dp),
            model = getImageUrl(id),
            loading = {
                Box(Modifier.size(size - 20.dp)) {
                    PokemonProgressIndicator()
                }
            },
            error = {
                Box(Modifier.size(size - 20.dp)) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "?",
                        color = Color(0xFF03a9f4),
                        style = TextStyle(fontSize = 30.dp.asSp())
                    )
                }
            },
            onSuccess = {
                MyPalette.getPalette(it.result.drawable.toBitmap()) { palette ->
                    if (palette != null) {
                        colorStart = palette.getDominantColor(Color.Transparent.toArgb())
                        colorEnd = palette.getLightVibrantColor(Color.Transparent.toArgb())
                        if (colorStart == 0) {
                            colorStart = colorEnd
                        }
                        if (colorEnd == 0) {
                            colorEnd = colorStart
                        }
                    }
                }
            },
            contentDescription = pokemon?.name
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp),
            text = String.format("#%03d", id),
            style = TextStyle(fontSize = 10.dp.asSp())
        )
    }
}