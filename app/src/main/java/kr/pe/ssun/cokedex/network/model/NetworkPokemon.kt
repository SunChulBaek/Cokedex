package kr.pe.ssun.cokedex.network.model

import kr.pe.ssun.cokedex.data.model.UiPokemon
import com.google.gson.annotations.SerializedName

data class NetworkPokemonList(
    @SerializedName("count") val count: Int,
    @SerializedName("previous") val prev: String,
    @SerializedName("next") val next: String,
    @SerializedName("results") val results: List<NetworkPokemon>,
)

data class NetworkPokemon(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

fun NetworkPokemon.asExternalModel() = this.url.split("/")[6].toInt().let { id ->
    UiPokemon(
        id = id,
        name = this.name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
    )
}