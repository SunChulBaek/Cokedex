package kr.pe.ssun.cokedex.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.R
import kr.pe.ssun.cokedex.ui.detail.PokemonDetailBg
import kr.pe.ssun.cokedex.ui.theme.CokedexTheme

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF009688),
    buttonSize: Dp = 50.dp,
    borderWidth: Dp = 5.dp,
    border2Width: Dp = 2.dp
) = Box(
    modifier = modifier
        .clip(CircleShape)
        .size(buttonSize)
        .clickable {}
        .drawWithContent {
            // border
            drawCircle(
                color = Color.White.copy(alpha = 0.4f),
                radius = (buttonSize / 2 - borderWidth / 2).toPx(),
                style = Stroke(width = borderWidth.toPx())
            )
            // 컬러 border
            drawCircle(
                color = color,
                radius = (buttonSize / 2 - borderWidth - border2Width / 2).toPx(),
                style = Stroke(width = 1.dp.toPx())
            )
            // 센터
            drawCircle(
                color = Color.White.copy(alpha = 0.8f),
                radius = (buttonSize / 2 - borderWidth - border2Width).toPx()
            )
            drawContent()
        },
) {
    Image(
        modifier = Modifier.align(Alignment.Center).size(buttonSize * 0.4f),
        painter = painterResource(id = R.drawable.baseline_close_24),
        colorFilter = ColorFilter.tint(Color(0xFF009688)),
        contentDescription = "close"
    )
}

@Preview
@Composable
fun CancelButtonPreview() = CokedexTheme {
    PokemonDetailBg(modifier = Modifier.size(300.dp)) {
        CancelButton(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 10.dp),
        )
    }
}