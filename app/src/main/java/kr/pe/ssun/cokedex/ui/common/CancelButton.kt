package kr.pe.ssun.cokedex.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.ui.detail.PokemonDetailBg
import kr.pe.ssun.cokedex.ui.theme.CokedexTheme
import kotlin.math.PI
import kotlin.math.cos

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF009688),
    buttonSize: Dp = 50.dp,
    borderWidth: Dp = 5.dp,
    border2Width: Dp = 2.dp,
    onClick: () -> Unit = {},
) = Spacer(
    modifier = modifier
        .clip(CircleShape)
        .size(buttonSize)
        .clickable { onClick() }
        .drawBehind {
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
                style = Stroke(width = border2Width.toPx())
            )
            // 센터
            val centerRadius = (buttonSize / 2 - borderWidth - border2Width).toPx()
            drawCircle(
                color = Color.White.copy(alpha = 0.8f),
                radius = centerRadius
            )
            // X
            val xOffset = centerRadius / 2 * cos(45 * PI / 180).toFloat()
            // 왼쪽 위 -> 오른쪽 아래
            drawLine(
                color = color,
                start = Offset(
                    size.width / 2 - xOffset,
                    size.width / 2 - xOffset
                ),
                end = Offset(
                    size.width / 2 + xOffset,
                    size.width / 2 + xOffset
                ),
                strokeWidth = border2Width.toPx()
            )
            // 오른쪽 위 -> 왼쪽 아래
            drawLine(
                color = color,
                start = Offset(
                    size.width / 2 + xOffset,
                    size.width / 2 - xOffset
                ),
                end = Offset(
                    size.width / 2 - xOffset,
                    size.width / 2 + xOffset
                ),
                strokeWidth = border2Width.toPx()
            )
        },
)

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