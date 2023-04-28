package kr.pe.ssun.cokedex.network.model

import kr.pe.ssun.cokedex.data.model.Photo
import com.google.gson.annotations.SerializedName

data class NetworkPhoto(
    @SerializedName("albumId") val albumId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String
)

fun NetworkPhoto.asExternalModel() = Photo(
    albumId = this.albumId,
    id = this.id,
    title = this.title,
    url = this.url,
    thumbnailUrl = this.thumbnailUrl,
)