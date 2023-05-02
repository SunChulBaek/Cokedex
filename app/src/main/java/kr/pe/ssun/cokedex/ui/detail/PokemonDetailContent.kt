package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import java.text.DecimalFormat

@Composable
fun PokemonDetailContent(
    uiState: PokemonUiState,
) {
    val pokemon = (uiState as PokemonUiState.Success).pokemon
    val colorStart = Color(uiState.colorStart)
    val colorEnd = Color(uiState.colorEnd)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    brush = Brush.verticalGradient(listOf(colorStart, colorEnd)),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
        ) {
            val imageRef = createRef()
            SubcomposeAsyncImage(
                modifier = Modifier.constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1:1")
                },
                model = pokemon?.imageUrl,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(25.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                contentDescription = "thumbnail"
            )
        }
        // 이름
        Text(text = pokemon.name, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        // 타입
        Row {
            Spacer(modifier = Modifier.weight(1f))
            pokemon.types?.forEach { type ->
                Text(
                    text = type.toString(),
                    modifier = Modifier
                        .background(type.getColor(), RoundedCornerShape(16.dp))
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // 몸무게, 키
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${DecimalFormat("#,##0.0").format(pokemon.weight.toFloat() / 10)} KG",
                    color = Color.White
                )
                Text(text = "Weight", color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${DecimalFormat("#,##0.0").format(pokemon.height.toFloat() / 10)} M",
                    color = Color.White
                )
                Text(text = "Height", color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(10.dp))
        // 스탯
        Text(text = "Base Stats", color = Color.White)
        pokemon.stats?.forEach { stat ->
            Row {
                Text(text = stat.name, color = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stat.value.toString(),
                    modifier = Modifier.weight(1f),
                    color = Color.White
                )
            }
        }
    }
}