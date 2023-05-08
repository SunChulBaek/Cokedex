package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAbilityEffectChange(
    @SerializedName("effect_entries") val effectEntries: List<NetworkEffect>,
    @SerializedName("version_group") val versionGroup: NetworkNamedAPIResource,
)