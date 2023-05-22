package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import java.util.Locale

data class NetworkMove(
    @SerializedName("id") val id: Int,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<NetworkAbilityFlavorText>,
)

