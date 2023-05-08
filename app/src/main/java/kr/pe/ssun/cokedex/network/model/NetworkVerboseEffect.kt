package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkVerboseEffect(
    @SerializedName("effect") val effect: String,
    @SerializedName("short_effect") val shortEffect: String,
    @SerializedName("language") val language: NetworkNamedAPIResource,
)