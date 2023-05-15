package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import java.util.Locale

data class NetworkName(
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: NetworkNamedAPIResource,
)

fun List<NetworkName>.getNameX(): String? = firstOrNull {
    it.language.name == Locale.getDefault().language
}?.name ?: firstOrNull {
    it.language.name == "en"
}?.name

fun List<NetworkName>.getNameLang(): String? = firstOrNull {
    it.language.name == Locale.getDefault().language
}?.language?.name ?: firstOrNull {
    it.language.name == "en"
}?.language?.name