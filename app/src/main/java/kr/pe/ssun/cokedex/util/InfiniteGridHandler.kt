package kr.pe.ssun.cokedex.util

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

// https://dev.to/luismierez/infinite-lazycolumn-in-jetpack-compose-44a4
@Composable
fun InfiniteGridHandler(
    gridState: LazyGridState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    // loadMore 값과 상관 없이 onLoadMore 터지는 현상이 있어 filter 걸어줌 (ex. 상세 페이지에서 홈으로 돌아올 때)
    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .filter { loadMore -> loadMore }
            .collect {
                onLoadMore()
            }
    }
}