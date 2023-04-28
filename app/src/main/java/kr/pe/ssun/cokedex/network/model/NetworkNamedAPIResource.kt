package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

// https://pokeapi.co/docs/v2#namedapiresource
data class NetworkNamedAPIResource(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)