package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemonMove(
    @SerializedName("move") val move: NetworkNamedAPIResource,
    @SerializedName("version_group_details") val versionGroupDetails: List<NetworkPokemonMoveVersion> = listOf()
)