package kr.pe.ssun.cokedex.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kr.pe.ssun.cokedex.R
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kr.pe.ssun.cokedex.domain.GetPokemonListParam
import kr.pe.ssun.cokedex.navigation.pokemonDetailNavigationRoute
import kr.pe.ssun.cokedex.ui.common.DefaultScreen

// 백 키 관련
const val BACK_PRESS_DELAY_TIME: Long = 2000
var backKeyPressedTime: Long = 0
var toast: Toast? = null

// 하단탭에 대한 네비게이션만 처리
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigate: (String, Any?) -> Unit,
    showToast: (String) -> Toast,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberAnimatedNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    // 백키 2회에 종료 처리
    BackCloseHandler(navController, showToast, onBack)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        DefaultScreen(
            modifier = Modifier.padding(innerPadding),
            isLoading = uiState is HomeUiState.Loading,
            isError = uiState is HomeUiState.Error
        ) {
            HomeContent(
                uiState = uiState,
                onClick = { pokemon ->
                    navigate(pokemonDetailNavigationRoute, pokemon)
                },
                onLoadMore = { offset ->
                    viewModel.param.value = GetPokemonListParam(offset = offset)
                }
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
fun MyTopAppBar() = TopAppBar(
    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
    title = { Text(stringResource(R.string.app_name))},
    navigationIcon = {
        IconButton(onClick = { }) {
            Icon(Icons.Default.Menu, "Menu")
        }
    }
)