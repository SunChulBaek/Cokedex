package kr.pe.ssun.cokedex.navigation

import android.util.Base64
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kr.pe.ssun.cokedex.ui.common.MyWebView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kr.pe.ssun.cokedex.data.model.UiPokemon

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    showToast: (String) -> Toast,
    startDestination: String = homeNavigationRoute,
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        // 홈
        homeScreen(
            enterTransition = defaultEnterTransition(),
            exitTransition = slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ),
            popEnterTransition = defaultPopEnterTransition(),
            popExitTransition = defaultPopExitTransition(),
            navigate = { route, params -> navigate(navController, route, params) },
            showToast = showToast,
            onBack = { navController.popBackStack() }
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
        composable(
            route = "webview/{url}",
            arguments = listOf(
                navArgument("url" ) { type = NavType.StringType }
            ),
            enterTransition = { defaultEnterTransition() },
            exitTransition = { defaultExitTransition() },
            popEnterTransition = { defaultPopEnterTransition() },
            popExitTransition = { defaultPopExitTransition() },
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url")
            val decodedUrl = String(Base64.decode(encodedUrl, 0))
            MyWebView(url = decodedUrl)
        }
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
        pokemonDetailNavigationRoute -> navController.navigateToPokemonDetail(params as UiPokemon)
        else -> TODO()
    }
}