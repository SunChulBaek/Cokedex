package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAbilityFlavorText(
    @SerializedName("flavor_text") val flavorText: String,
    @SerializedName("language") val language: NetworkNamedAPIResource,
    @SerializedName("version_group") val versionGroup: NetworkNamedAPIResource? = null,
)