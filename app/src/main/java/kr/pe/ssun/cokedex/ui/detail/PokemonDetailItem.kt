package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.model.EvolutionChain
import kr.pe.ssun.cokedex.model.Form
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.model.Species
import kr.pe.ssun.cokedex.model.Type
import kr.pe.ssun.cokedex.ui.common.PokemonProgressIndicator
import kr.pe.ssun.cokedex.ui.detail.PokemonDetailItem.Companion.STAT_FONT_SIZE
import kr.pe.ssun.cokedex.util.asSp
import java.text.DecimalFormat

interface PokemonDetailItem {
    companion object {
        const val STAT_FONT_SIZE = 12
    }

    @Composable
    fun ItemContent(onClick: (Pokemon) -> Unit)
}

data class PokemonDetailImage(
    val id: Int
) : PokemonDetailItem {

    @Composable
    override fun ItemContent(onClick: (Pokemon) -> Unit) = ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val imageRef = createRef()
        SubcomposeAsyncImage(
            modifier = Modifier
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.ratio("1:1")
                    height = Dimension.value(200.dp)
                }
                .padding(top = 20.dp),
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
            loading = {
                Box {
                    PokemonProgressIndicator(modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center))
                }
            },
            contentDescription = "thumbnail"
        )
    }
}

data class PokemonDetailName(
    val id: Int,
    val name: String,
    val species: Species? = null,
    val form: Form? = null,
) : PokemonDetailItem {

    @Composable
    override fun ItemContent(onClick: (Pokemon) -> Unit) = Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "${String.format("%04d", id)} ${species?.getName() ?: name} ${if (form?.getName() != null) "(${form.getName()})" else ""}",
            style = TextStyle(fontSize = if (form?.getName() != null) 20.dp.asSp() else 30.dp.asSp()),
            color = Color.White,
        )
    }
}

data class PokemonDetailStat(
    val weight: Float,
    val types: List<Type>,
    val height: Float,
) : PokemonDetailItem {

    @Composable
    override fun ItemContent(onClick: (Pokemon) -> Unit) = Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(horizontal = 70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "몸무게: ${DecimalFormat("#,##0.0").format(weight)} kg",
            style = TextStyle(fontSize = STAT_FONT_SIZE.dp.asSp()),
            color = Color.White,
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "키: ${DecimalFormat("#,##0.0").format(height)}m",
            style = TextStyle(fontSize = STAT_FONT_SIZE.dp.asSp()),
            color = Color.White,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "타입: ",
                style = TextStyle(fontSize = STAT_FONT_SIZE.dp.asSp()),
                color = Color.White
            )
            types.forEachIndexed { index, type ->
                if (index > 0) {
                    Text(
                        text = "/",
                        style = TextStyle(fontSize = STAT_FONT_SIZE.dp.asSp()),
                        color = Color.White
                    )
                }
                Text(
                    text = type.getName() ?: "",
                    style = TextStyle(fontSize = STAT_FONT_SIZE.dp.asSp()),
                    color = Color.White,
                )
            }
        }
    }
}

data class PokemonDetailFlavorText(
    val species: Species
) : PokemonDetailItem {

    @Composable
    override fun ItemContent(onClick: (Pokemon) -> Unit) = Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = species.getFlavorText() ?: "",
        style = TextStyle(fontSize = 16.dp.asSp()),
        color = Color.White,
    )
}

class PokemonDetailEvolution(
    val evolutionChain: EvolutionChain,
    val hostId: Int
) : PokemonDetailItem {

    @Composable
    override fun ItemContent(onClick: (Pokemon) -> Unit) = Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.White))
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "진화",
            style = TextStyle(fontSize = 18.dp.asSp()),
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(20.dp))
        PokemonEvolutionChains(
            modifier = Modifier.padding(horizontal = 30.dp),
            evolutionChain = evolutionChain,
            hostId = hostId,
            onClick = onClick
        )
    }
}

class PokemonDetailVarieties(
    val varietyIds: List<Int>?,
    val hostId: Int,
) : PokemonDetailItem {

    @Composable
    override fun ItemContent(onClick: (Pokemon) -> Unit) = Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.White))
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Varieties",
            style = TextStyle(fontSize = 18.dp.asSp()),
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(20.dp))
        PokemonVarieties(
            modifier = Modifier.padding(horizontal = 30.dp),
            varietyIds = varietyIds,
            hostId = hostId,
            onClick = onClick
        )
    }
}