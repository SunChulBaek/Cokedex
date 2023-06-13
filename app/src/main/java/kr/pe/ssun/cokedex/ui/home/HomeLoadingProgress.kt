package kr.pe.ssun.cokedex.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.util.asSp

@Composable
fun HomeLoadingProgress(
    modifier: Modifier = Modifier,
    uiState: LazyPagingItems<Pokemon>,
    colorFromInternet: Color = Color(0xFF4caf50),
    colorFromDB: Color = Color(0xFF3f51b5),
) = Row(
    modifier = modifier.background(Color(0xFFe0e0e0))
) {
    val fromDbSize = uiState.itemSnapshotList.filter { it?.fromDB == true }.size
    if (fromDbSize > 0) {
        Box(
            modifier = Modifier
                .weight(fromDbSize.toFloat())
                .fillMaxHeight()
                .background(colorFromDB)
                .border(0.3.dp, Color.Black)
        ) {
            Text(
                modifier = Modifier.padding(start = 5.dp).align(Alignment.CenterStart),
                text = "fromDB($fromDbSize)",
                style = TextStyle(fontSize = 7.dp.asSp()),
                color = Color.White
            )
        }
    }

    val fromApiSize = uiState.itemSnapshotList.filter { it?.fromDB != true }.size
    if (fromApiSize > 0) {
        Box(
            modifier = Modifier
                .weight(fromApiSize.toFloat())
                .fillMaxHeight()
                .background(colorFromInternet)
                .border(0.3.dp, Color.Black)
        ) {
            Text(
                modifier = Modifier.padding(start = 5.dp).align(Alignment.CenterStart),
                text = "fromAPI($fromApiSize)",
                style = TextStyle(fontSize = 7.dp.asSp()),
                color = Color.White
            )
        }
    }
}