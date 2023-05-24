package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.common.getImageUrl

@Composable
fun HomeShortcuts(
    modifier: Modifier = Modifier,
    shortcuts: List<Int>,
    onClick: (Pokemon) -> Unit,
) = LazyRow(
    modifier = modifier,
    contentPadding = PaddingValues(horizontal = 10.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    items(
        count = shortcuts.size,
        itemContent = { index ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        onClick(
                            Pokemon(
                                id = shortcuts[index],
                                name = "Name",
                                fallbackName = "Name",
                                imageUrl = getImageUrl(shortcuts[index]),
                            )
                        )
                    }
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.Center),
                    model = getImageUrl(shortcuts[index]),
                    contentDescription = null
                )
            }
        }
    )
}