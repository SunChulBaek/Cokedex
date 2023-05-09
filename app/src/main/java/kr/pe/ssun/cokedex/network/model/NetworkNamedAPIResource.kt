package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.data.model.UiPokemon

// https://pokeapi.co/docs/v2#namedapiresource
data class NetworkNamedAPIResource(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String? = null,
)

fun NetworkNamedAPIResource.asExternalPokemonModel() = url?.split("/")?.get(6)?.toInt()?.let { id ->
    UiPokemon(
        id = id,
        name = this.name ?: "",
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
    )
}