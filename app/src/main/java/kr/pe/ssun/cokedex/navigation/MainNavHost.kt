package kr.pe.ssun.cokedex.navigation

import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kr.pe.ssun.cokedex.model.Pokemon

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    showToast: (String) -> Toast,
    onBack: () -> Unit,
    startDestination: String = homeNavigationRoute,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        // 홈
        homeScreen(
            enterTransition = defaultEnterTransition(),
            exitTransition = defaultExitTransition(),
            popEnterTransition = defaultPopEnterTransition(),
            popExitTransition = defaultPopExitTransition(),
            navigate = { route, params -> navigate(navController, route, params) },
            showToast = showToast,
            onBack = { onBack() }
        )
        // 포토
        pokemonDetailScreen(
            enterTransition = defaultEnterTransition(),
            exitTransition = defaultExitTransition(),
            popEnterTransition = defaultPopEnterTransition(),
            popExitTransition = defaultPopExitTransition(),
            navigate = { route, params -> navigate(navController, route, params) },
            showToast = showToast,
            onBack = { navController.popBackStack() }
        )
        // 웹뷰
        webViewScreen(
            enterTransition = defaultEnterTransition(),
            exitTransition = defaultExitTransition(),
            popEnterTransition = defaultPopEnterTransition(),
            popExitTransition = defaultPopExitTransition(),
            navigate = { route, params -> navigate(navController, route, params) },
            showToast = showToast,
            onBack = { navController.popBackStack() }
        )
    }
}

fun defaultEnterTransition(): EnterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
)

fun defaultExitTransition(): ExitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> -fullWidth },
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
)

fun defaultPopEnterTransition(): EnterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> -fullWidth },
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
)

fun defaultPopExitTransition(): ExitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
)

fun navigate(
    navController: NavHostController,
    dest: String,
    params: Any? = null,
) {
    when (dest) {
        pokemonDetailNavigationRoute -> navController.navigateToPokemonDetail(params as Pokemon)
        webViewNavigationRoute -> navController.navigateToWebView(params as String)
        else -> TODO()
    }
}