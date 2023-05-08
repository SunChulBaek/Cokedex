package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemonHeldItemVersion(
    @SerializedName("version") val version: NetworkNamedAPIResource,
    @SerializedName("rarity") val rarity: Int,
)