package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkName(
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: NetworkNamedAPIResource,
)