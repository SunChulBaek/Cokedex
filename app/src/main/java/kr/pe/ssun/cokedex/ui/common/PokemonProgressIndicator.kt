package kr.pe.ssun.cokedex.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun PokemonProgressIndicator(
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    color: Color = Color.Red
) = ConstraintLayout(modifier = modifier) {
    val animation = rememberInfiniteTransition()
    val rotation by animation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            )
        )
    )
    val ref = createRef()
    MonsterBall(
        modifier = Modifier
            .constrainAs(ref) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .aspectRatio(1f)
            .rotate(rotation),
        color = color
    )
}

@Composable
private fun MonsterBall(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
) = Canvas(modifier = modifier) {
    val radius = minOf(size.width, size.height) / 2 // width, height 중 작은 길이에 맞춰서 그림
    val borderWidth = radius * 0.1f
    val divider = radius * 0.2f
    clipPath(
        path = Path().apply {
            addOval(
                Rect(
                    offset = Offset(size.width / 2 - radius, size.height / 2 - radius),
                    size = Size(radius * 2, radius * 2)
                )
            )
        }
    ) {
        drawRect(
            color = color,
            topLeft = Offset(size.width / 2 - radius, size.height / 2 - radius),
            size = Size(radius * 2, radius)
        )
        drawRect(
            color = Color.White,
            topLeft = Offset(size.width / 2 - radius, size.height / 2),
            size = Size(radius * 2, radius)
        )
        // divider
        drawRect(
            Color.Black,
            topLeft = Offset(size.width / 2 - radius, size.height / 2 - divider / 2),
            size = Size(radius * 2, divider)
        )
        // border
        drawCircle(
            color = Color.Black,
            radius = radius - borderWidth / 2,
            style = Stroke(width = borderWidth)
        )
        drawCircle(
            color = Color.Black,
            radius = radius * 0.4f
        )
        drawCircle(
            color = Color.White,
            radius = radius * 0.2f
        )
    }
}

@Composable
@Preview
fun MonsterBallPreview() = Column {
    MonsterBall(
        Modifier
            .size(50.dp)
            .background(Color.Green),
        Color.Blue
    )
    Spacer(modifier = Modifier.height(10.dp))
    MonsterBall(
        Modifier
            .width(100.dp)
            .height(50.dp)
            .background(Color.Green))
    Spacer(modifier = Modifier.height(10.dp))
    MonsterBall(
        Modifier
            .width(50.dp)
            .height(100.dp)
            .background(Color.Green))
}

@Composable
@Preview
fun PokemonProgressPreview() = Column {
    PokemonProgressIndicator(
        modifier = Modifier
            .size(50.dp)
            .background(Color.Yellow),
        color = Color.Blue
    )
    Spacer(modifier = Modifier.height(10.dp))
    PokemonProgressIndicator(
        modifier = Modifier
            .width(100.dp)
            .height(50.dp)
            .background(Color.Yellow)
    )
    Spacer(modifier = Modifier.height(10.dp))
    PokemonProgressIndicator(
        Modifier
            .width(50.dp)
            .height(100.dp)
            .background(Color.Yellow)
    )
}