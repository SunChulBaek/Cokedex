package kr.pe.ssun.cokedex.navigation

import android.util.Base64
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.pe.ssun.cokedex.ui.common.MyWebView

const val webViewNavigationRoute = "webView"

const val webViewUrlArg = "url"

fun NavController.navigateToWebView(url: String, navOptions: NavOptions? = null) {
    val encoded = Base64.encodeToString(url.toByteArray(), Base64.DEFAULT)
    this.navigate("$webViewNavigationRoute/$encoded")
}

fun NavGraphBuilder.webViewScreen(
    enterTransition: EnterTransition = EnterTransition.None,
    exitTransition: ExitTransition = ExitTransition.None,
    popEnterTransition: EnterTransition = EnterTransition.None,
    popExitTransition: ExitTransition = ExitTransition.None,
    navigate: (String, Any?) -> Unit,
    showToast: (String) -> Toast,
    onBack: () -> Unit,
) {
    composable(
        route = "$webViewNavigationRoute/{$webViewUrlArg}",
        arguments = listOf(
            navArgument(webViewUrlArg) { type = NavType.StringType }
        ),
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition },
    ) { backStackEntry ->
        val encodedUrl = backStackEntry.arguments?.getString(webViewUrlArg)
        val decodedUrl = String(Base64.decode(encodedUrl, 0))
        MyWebView(url = decodedUrl, onBack = onBack)
    }
}