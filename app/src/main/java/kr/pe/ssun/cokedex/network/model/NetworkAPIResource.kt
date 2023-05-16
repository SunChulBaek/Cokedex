package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAPIResource(
    @SerializedName("url") val url: String?
) {
    fun getId(): Int = url?.split("/")?.let { splits ->
        if (splits.size > 6) {
            splits[6].toInt()
        } else {
            0
        }
    } ?: 0
}