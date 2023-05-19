package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemonSpeciesVariety(
    @SerializedName("is_default") val isDefault: Boolean,
    @SerializedName("pokemon") val pokemon: NetworkNamedAPIResource,
) {
    fun getId() = pokemon.getId()
}