package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

// https://pokeapi.co/docs/v2#pokemontype
data class NetworkPokemonType(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: NetworkNamedAPIResource,
) {
    fun getId() = type.getId()
}