package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailContent(
    uiState: PokemonUiState,
    onBack: () -> Unit
) {
    val success = uiState as? PokemonUiState.Success
    val loading = uiState as? PokemonUiState.Loading

    val scrollState = rememberScrollState()

    val pokemon = success?.pokemon
    val id = pokemon?.id ?: loading?.id ?: 0
    val name = pokemon?.name ?: loading?.name ?: ""
    val imageUrl = pokemon?.imageUrl ?: loading?.imageUrl
    val colorStart = Color(success?.colorStart ?: loading?.colorStart ?: 0x00000000)
    val colorEnd = Color(success?.colorEnd ?: loading?.colorEnd ?: 0x00000000)
    val totalAbilitiesCount = pokemon?.totalAbilitiesCount ?: 0
    val totalMovesCount = pokemon?.totalMovesCount ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121)),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorStart),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Cokedex")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(String.format("#%03d", id))
                    }
                }
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        brush = Brush.verticalGradient(listOf(colorStart, colorEnd)),
                        shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
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
                    model = imageUrl,
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
            Text(text = name, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            // 타입
            Row {
                Spacer(modifier = Modifier.weight(1f))
                pokemon?.types?.forEach { type ->
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
                        text = "${DecimalFormat("#,##0.0").format((pokemon?.weight?.toFloat() ?: 0f) / 10)} KG",
                        color = Color.White
                    )
                    Text(text = "Weight", color = Color.White)
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${DecimalFormat("#,##0.0").format((pokemon?.height?.toFloat() ?: 0f) / 10)} M",
                        color = Color.White
                    )
                    Text(text = "Height", color = Color.White)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(10.dp))
            // 스탯
            Text(text = "Base Stats", color = Color.White)
            pokemon?.stats?.forEach { stat ->
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
            Spacer(modifier = Modifier.height(10.dp))
            // abilities
            Text(
                text = "Abilities ${pokemon?.abilities?.size ?: 0}/$totalAbilitiesCount",
                color = Color.White
            )
            pokemon?.abilities?.forEach { ability ->
                Row {
                    Text(text = ability.name ?: "", color = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = ability.flavor ?: "",
                        modifier = Modifier.weight(1f),
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // moves
            Text(text = "Moves ${pokemon?.moves?.size ?: 0}/$totalMovesCount", color = Color.White)
            pokemon?.moves?.forEach { move ->
                Row {
                    Text(text = move.name ?: "", color = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = move.flavor ?: "",
                        modifier = Modifier.weight(1f),
                        color = Color.White
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth().height(5.dp)
                .background(Color(0xFFe0e0e0))
        ) {
            for (i in 0 until totalAbilitiesCount) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().background(
                    when (pokemon?.abilities?.getOrNull(i)?.fromDB) {
                        true -> Color(0xFF2196f3)
                        false -> Color(0xFFe91e63)
                        else -> Color.Transparent
                    }
                )) {

                }
            }
            for (j in 0 until totalMovesCount) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().background(
                    when (pokemon?.moves?.getOrNull(j)?.fromDB) {
                        true -> Color(0xFF2196f3)
                        false -> Color(0xFFe91e63)
                        else -> Color.Transparent
                    }
                )) {

                }
            }
        }
    }
}