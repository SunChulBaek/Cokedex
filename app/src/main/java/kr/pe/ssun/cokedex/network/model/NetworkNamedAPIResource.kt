package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.common.getImageUrl

// https://pokeapi.co/docs/v2#namedapiresource
data class NetworkNamedAPIResource(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String? = null,
) {
    fun getId(): Int = url?.split("/")?.let { splits ->
        if (splits.size > 6) {
            splits[6].toInt()
        } else {
            0
        }
    } ?: 0
}

fun NetworkNamedAPIResource.asExternalModel() = Pokemon(
    id = getId(),
    name = this.name ?: "",
    fallbackName = this.name ?: "",
    imageUrl = getImageUrl(getId()),
)