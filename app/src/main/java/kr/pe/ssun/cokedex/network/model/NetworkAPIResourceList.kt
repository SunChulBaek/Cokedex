package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.data.model.UiPokemon

data class NetworkAPIResourceList(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val prev: String,
    @SerializedName("results") val results: List<NetworkNamedAPIResource>,
)

fun NetworkNamedAPIResource.asExternalPokemonModel() = this.url.split("/")[6].toInt().let { id ->
    UiPokemon(
        id = id,
        name = this.name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
    )
}