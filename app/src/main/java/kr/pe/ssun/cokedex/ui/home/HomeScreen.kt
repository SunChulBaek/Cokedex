package kr.pe.ssun.cokedex.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kr.pe.ssun.cokedex.R
import kotlinx.coroutines.launch
import kr.pe.ssun.cokedex.BuildConfig
import kr.pe.ssun.cokedex.navigation.pokemonDetailNavigationRoute
import kr.pe.ssun.cokedex.ui.common.DefaultScreen
import kr.pe.ssun.cokedex.ui.common.SimpleDialog

// 백 키 관련
const val BACK_PRESS_DELAY_TIME: Long = 2000
var backKeyPressedTime: Long = 0
var toast: Toast? = null

// 하단탭에 대한 네비게이션만 처리
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigate: (String, Any?) -> Unit,
    showToast: (String) -> Toast,
    onBack: () -> Unit
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val pokemonList = viewModel.pagingFlow.collectAsLazyPagingItems()
    val firstLoad = pokemonList.loadState.refresh // 최초 로딩 상태
    val searchResult = viewModel.searchResult.collectAsStateWithLifecycle()

    val showInfo = viewModel.showInfo.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    // 백키 2회에 종료 처리
    BackCloseHandler(navController, showToast, onBack)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar(showVersionInfo = {
            viewModel.toggleShowInfo()
        })},
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        DefaultScreen(
            modifier = Modifier.padding(innerPadding),
            isLoading = firstLoad is LoadState.Loading,
            isError = firstLoad is LoadState.Error
        ) {
            HomeContent(
                uiState = pokemonList,
                searchResult = searchResult.value,
                onSearch = { newText ->
                    scope.launch {
                        viewModel.search.emit(newText)
                    }
                },
                onClick = { pokemon ->
                    navigate(pokemonDetailNavigationRoute, pokemon)
                },
            )
        }
        AnimatedVisibility(
            visible = showInfo.value,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            SimpleDialog(
                title = "Pokedex with Compose",
                message = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})\nhttps://github.com/SunChulBaek/Cokedex",
                onDismissRequest = { viewModel.toggleShowInfo() },
                onOkClick = { viewModel.toggleShowInfo() },
            )
        }
    }
}

/**
 * 백키 2회에 종료 처리
 */
@Composable
fun BackCloseHandler(
    navController: NavHostController,
    showToast: (String) -> Toast,
    onBack: () -> Unit
) = BackHandler {
    if (!navController.popBackStack()) {
        if (System.currentTimeMillis() > backKeyPressedTime + BACK_PRESS_DELAY_TIME) {
            backKeyPressedTime = System.currentTimeMillis()
            toast = showToast("\'뒤로\' 버튼 한번 더 누르시면 종료됩니다.")
            return@BackHandler
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + BACK_PRESS_DELAY_TIME) {
            toast?.cancel()
            onBack.invoke()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    showVersionInfo: () -> Unit,
) = TopAppBar(
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        navigationIconContentColor = Color.Black,
        titleContentColor = Color.Black,
        actionIconContentColor = Color.Black
    ),
    title = { Text(stringResource(R.string.app_name))},
    actions = {
        IconButton(onClick = showVersionInfo) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "More",
                tint = Color.Black
            )
        }
    }
)