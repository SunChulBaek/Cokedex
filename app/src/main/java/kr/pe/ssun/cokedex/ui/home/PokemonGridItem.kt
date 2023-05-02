package kr.pe.ssun.cokedex.ui.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.data.model.UiPokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonGridItem(
    modifier: Modifier = Modifier,
    item: UiPokemon,
    onClick: () -> Unit
) = Card(
    modifier = modifier,
    onClick = onClick
) {
    ConstraintLayout(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
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
            contentDescription = null
        )
        Text(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(imageRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = item.name
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