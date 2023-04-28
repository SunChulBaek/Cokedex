package kr.pe.ssun.cokedex.navigation

import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import kr.pe.ssun.cokedex.ui.detail.PokemonDetailRoute
import com.google.accompanist.navigation.animation.composable
import kr.pe.ssun.cokedex.data.model.Pokemon

const val pokemonDetailNavigationRoute = "pokemon_detail"

const val pokemonDetailIdArg = "id"
const val pokemonDetailNameArg = "name"
const val pokemonDetailUrlArg = "imageUrl"

internal class PokemonDetailArgs(val id: Int, val name: String, val imageUrl: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        id = Uri.decode(checkNotNull(savedStateHandle[pokemonDetailIdArg])).toInt(),
        name = Uri.decode(checkNotNull(savedStateHandle[pokemonDetailNameArg])),
        imageUrl = Uri.decode(checkNotNull(savedStateHandle[pokemonDetailUrlArg])).run { String(Base64.decode(this, 0)) },
    )
}

fun NavController.navigateToPokemonDetail(pokemon: Pokemon, navOptions: NavOptions? = null) {
    val encoded = Base64.encodeToString(pokemon.imageUrl.toByteArray(), Base64.DEFAULT)
    this.navigate("$pokemonDetailNavigationRoute/${pokemon.id}/${pokemon.name}/$encoded", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.pokemonDetailScreen(
    enterTransition: EnterTransition = EnterTransition.None,
    exitTransition: ExitTransition = ExitTransition.None,
    popEnterTransition: EnterTransition = EnterTransition.None,
    popExitTransition: ExitTransition = ExitTransition.None,
    navigate: (String, Any?) -> Unit,
    showToast: (String) -> Toast,
    onBack: () -> Unit,
) {
    composable(
        route = "$pokemonDetailNavigationRoute/{$pokemonDetailIdArg}/{$pokemonDetailNameArg}/{$pokemonDetailUrlArg}",
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition },
    ) {
        PokemonDetailRoute()
    }
}