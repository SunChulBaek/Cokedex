package kr.pe.ssun.cokedex.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.pe.ssun.cokedex.ui.detail.PokemonDetailBg
import kr.pe.ssun.cokedex.ui.theme.CokedexTheme
import kr.pe.ssun.cokedex.util.cosDeg

private const val rippleDuration = 300

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF009688),
    buttonSize: Dp = 50.dp,
    borderWidth: Dp = 5.dp,
    border2Width: Dp = 2.dp,
    onClick: () -> Unit = {},
) {
    val centerRadius = with(LocalDensity.current) { (buttonSize / 2 - borderWidth - border2Width).toPx() }
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val screenHeight = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    val scope = rememberCoroutineScope()
    // ripple
    var startRipple by remember { mutableStateOf(false) }
    val rippleColor = remember { Animatable(0.8f) }
    // inner ripple
    val innerRippleSize = remember { Animatable(centerRadius) }
    // outer ripple
    val outerRippleSize = remember { Animatable(centerRadius * 3) }

    LaunchedEffect(startRipple) {
        if (startRipple) {
            scope.launch {
                rippleColor.animateTo(
                    0f,
                    animationSpec = tween(rippleDuration)
                )
            }
            scope.launch {
                innerRippleSize.animateTo(
                    centerRadius * 3,
                    animationSpec = tween(rippleDuration)
                )
            }
            scope.launch {
                outerRippleSize.animateTo(
                    centerRadius * 10,
                    animationSpec = tween(rippleDuration)
                )
            }
        }
    }
    Spacer(
        modifier = modifier
            .size(buttonSize)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                startRipple = true
                scope.launch {
                    delay(rippleDuration.toLong())
                    onClick()
                }
            }
            .drawBehind {this.
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
                val xOffset = centerRadius / 2 * cosDeg(45).toFloat()
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
                // Ripple
                if (startRipple) {
                    drawCircle(
                        color = Color.White.copy(alpha = rippleColor.value),
                        radius = innerRippleSize.value
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = rippleColor.value),
                        radius = outerRippleSize.value,
                        style = Stroke(width = 10.dp.toPx())
                    )
                }
            },
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