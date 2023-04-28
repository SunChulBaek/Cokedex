package kr.pe.ssun.cokedex.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)