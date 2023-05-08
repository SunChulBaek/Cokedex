package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemonTypePast(
    @SerializedName("generation") val generation: NetworkNamedAPIResource,
    @SerializedName("types") val types: List<NetworkPokemonType>,
)