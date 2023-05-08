package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAPIResourceList(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val prev: String,
    @SerializedName("results") val results: List<NetworkNamedAPIResource>,
)