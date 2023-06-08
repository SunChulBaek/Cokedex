package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.ui.theme.CokedexTheme
import kr.pe.ssun.cokedex.util.asPx
import kotlin.math.PI
import kotlin.math.tan

private const val lineAlpha = 0.05f
private const val lineAlphaAccent = 0.1f

@Composable
fun PokemonDetailBg(
    modifier: Modifier = Modifier,
    tileSize: Int = 100,
    lineColor: Color = Color.White,
    strokeWidth: Float = 5f,
    content: @Composable BoxScope.() -> Unit,
) {
    val left = remember { Animatable(0f) }
    val screenWidthPx = Dp(LocalConfiguration.current.screenWidthDp.toFloat()).asPx()
    val screenWidthFactor = (screenWidthPx / tileSize).toInt() // 애니메이션 걸리는 현상 없애기 위해서 Int로 자름
    LaunchedEffect(left) {
        left.animateTo((tileSize * screenWidthFactor).toFloat(), animationSpec = infiniteRepeatable(
            animation = tween(
                7_000,
                easing = LinearEasing
            )
        ))
    }
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFd1c4e9),
                        Color(0xFFb39ddb),
                        Color(0xFF9fa8da),
                    ),
                    start = Offset.Zero,
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .drawWithContent {
                val height = tileSize * tan(60 * PI / 180)
                translate(left = left.value) {
                    clipRect(left = -size.width, top = 0f, right = size.width, bottom = size.height) {
                        // 왼쪽위 -> 오른쪽 아래
                        for (i in -(size.height.toInt() / tileSize)..size.width.toInt() / tileSize) {
                            drawLine(
                                color = lineColor.copy(alpha = if (i % 4 == 0) lineAlphaAccent else lineAlpha),
                                start = Offset(
                                    x = (i * tileSize).toFloat(),
                                    y = 0f
                                ),
                                end = Offset(
                                    x = (size.height / tan(60 * PI / 180) + (i * tileSize).toFloat()).toFloat(),
                                    y = size.height
                                ),
                                strokeWidth = strokeWidth
                            )
                        }
                        // 오른쪽위 -> 왼쪽 아래
                        for (i in 0..(size.width + size.height).toInt() / tileSize) {
                            drawLine(
                                color = lineColor.copy(alpha = if (i % 4 == 0) lineAlphaAccent else lineAlpha),
                                start = Offset(
                                    x = (i * tileSize).toFloat(),
                                    y = 0f
                                ),
                                end = Offset(
                                    x = (-size.height / tan(60 * PI / 180) + (i * tileSize)).toFloat(),
                                    y = size.height
                                ),
                                strokeWidth = strokeWidth
                            )
                        }
                        // 직선
                        for (i in 0..(size.height / height * 2).toInt()) {
                            drawLine(
                                color = lineColor.copy(alpha = if (i % 4 == 0) lineAlphaAccent else lineAlpha),
                                start = Offset(
                                    x = -size.width,
                                    y = (i * height / 2).toFloat()
                                ),
                                end = Offset(
                                    x = size.width,
                                    y = (i * height / 2).toFloat()
                                ),
                                strokeWidth = strokeWidth
                            )
                        }
                    }
                }
                drawContent()
            },
        content = content
    )
}

@Preview
@Composable
fun PokemonDetailBgPreview() = CokedexTheme {
    Column {
        PokemonDetailBg(
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFede7f6),
                            Color(0xFFb39ddb),
                            Color(0xFF9fa8da),
                        ),
                        start = Offset.Zero,
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                ),
            lineColor = Color.White,
        ) {}
        Spacer(modifier = Modifier.height(10.dp))
        PokemonDetailBg(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFede7f6),
                            Color(0xFFb39ddb),
                            Color(0xFF9fa8da),
                        ),
                        start = Offset.Zero,
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                ),
            lineColor = Color.White,
        ) {}
    }
}