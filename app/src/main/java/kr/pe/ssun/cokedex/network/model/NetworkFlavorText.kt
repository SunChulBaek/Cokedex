package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import java.util.Locale

data class NetworkFlavorText(
    @SerializedName("flavor_text") val flavorText: String,
    @SerializedName("language") val language: NetworkNamedAPIResource,
    @SerializedName("version") val version: NetworkNamedAPIResource,
)

fun List<NetworkFlavorText>.getFlavorText() = (firstOrNull {
    it.language.name == Locale.getDefault().language
}?.flavorText ?: firstOrNull {
    it.language.name == "en"
}?.flavorText)?.replace("\n", " ")