package kr.pe.ssun.cokedex.navigation

import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kr.pe.ssun.cokedex.ui.detail.PokemonDetailRoute
import kr.pe.ssun.cokedex.model.Pokemon

const val pokemonDetailNavigationRoute = "pokemon_detail"

const val pokemonDetailIdArg = "id"
const val pokemonDetailNameArg = "name"
const val pokemonDetailUrlArg = "imageUrl"
const val pokemonDetailColorStartArg = "colorStart"
const val pokemonDetailColorEndArg = "colorEnd"

internal class PokemonDetailArgs(val id: Int, val name: String, val imageUrl: String, val colorStart: Int, val colorEnd: Int) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        id = checkNotNull(savedStateHandle[pokemonDetailIdArg]).toString().toInt(),
        name = checkNotNull(savedStateHandle[pokemonDetailNameArg]),
        imageUrl = Uri.decode(checkNotNull(savedStateHandle[pokemonDetailUrlArg])),
        colorStart = checkNotNull(savedStateHandle[pokemonDetailColorStartArg]).toString().toInt(),
        colorEnd = checkNotNull(savedStateHandle[pokemonDetailColorEndArg]).toString().toInt(),
    )
}

fun NavController.navigateToPokemonDetail(pokemon: Pokemon, navOptions: NavOptions? = null) {
    val encodedImageUrl = Uri.encode(pokemon.imageUrl)
    this.navigate("$pokemonDetailNavigationRoute/${pokemon.id}/${pokemon.name.ifBlank { pokemon.fallbackName }}/$encodedImageUrl/${pokemon.colorStart}/${pokemon.colorEnd}", navOptions)
}

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
        route = "$pokemonDetailNavigationRoute/{$pokemonDetailIdArg}/{$pokemonDetailNameArg}/{$pokemonDetailUrlArg}/{$pokemonDetailColorStartArg}/{$pokemonDetailColorEndArg}",
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition },
    ) {
        PokemonDetailRoute(navigate = navigate, onBack = onBack)
    }
}