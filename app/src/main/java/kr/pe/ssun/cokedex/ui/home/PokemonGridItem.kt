package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.graphics.drawable.toBitmap
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.util.MyPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonGridItem(
    modifier: Modifier = Modifier,
    item: Pokemon,
    onClick: (Int, Int) -> Unit
) {
    var colorStart by remember { mutableStateOf(Color.Transparent.toArgb()) }
    var colorEnd by remember { mutableStateOf(Color.Transparent.toArgb()) }
    Card(
        modifier = modifier,
        onClick = { onClick(colorStart, colorEnd) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(Color(colorStart))
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            val (imageRef, titleRef, idRef) = createRefs()
            SubcomposeAsyncImage(
                modifier = Modifier.constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1:1")
                },
                model = item.imageUrl,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
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
                contentDescription = null
            )
            Text(
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(imageRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = if (item.name.isNotBlank()) item.name else item.fallbackName
            )
            Text(
                modifier = Modifier.constrainAs(idRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = item.id.toString()
            )
        }
    }
}