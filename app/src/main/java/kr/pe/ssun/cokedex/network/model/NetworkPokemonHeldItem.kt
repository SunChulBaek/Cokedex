package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemonHeldItem(
    @SerializedName("item") val item: NetworkNamedAPIResource,
    @SerializedName("version_details") val versionDetails: List<NetworkPokemonHeldItemVersion>,
)