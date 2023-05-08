package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkVersionGameIndex(
    @SerializedName("game_index") val gameIndex: Int,
    @SerializedName("version") val version: NetworkNamedAPIResource,
)