package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkEvolutionDetail(
    @SerializedName("item") val item: NetworkNamedAPIResource
)