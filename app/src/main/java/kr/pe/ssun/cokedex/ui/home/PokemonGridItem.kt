package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.common.PokemonProgressIndicator
import kr.pe.ssun.cokedex.ui.theme.Green100
import kr.pe.ssun.cokedex.ui.theme.Green50
import kr.pe.ssun.cokedex.util.asSp

@Composable
fun PokemonGridItem(
    modifier: Modifier = Modifier,
    item: Pokemon,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .background(
                Brush.radialGradient(
                    colors = listOf(Green100, Green50),
                )
            )
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        val (imageRef, idRef) = createRefs()
        // 이미지
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
                    PokemonProgressIndicator(modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center))
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "?",
                        color = Color(0xFF03a9f4),
                        style = TextStyle(fontSize = 50.dp.asSp())
                    )
                }
            },
            contentDescription = null
        )

        // 번호
        Text(
            modifier = Modifier
                .constrainAs(idRef) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(Green100, RoundedCornerShape(4.dp))
                .padding(horizontal = 2.dp),
            text = item.id.toString(),
            color = Color.White,
            style = TextStyle(fontSize = 8.dp.asSp())
        )
    }
}