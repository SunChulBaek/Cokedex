package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.PokemonItemEntity

data class NetworkAPIResourceList(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val prev: String,
    @SerializedName("results") val results: List<NetworkNamedAPIResource>,
)

fun NetworkAPIResourceList.asEntity(offset: Int) = results.mapIndexed { index, item ->
    PokemonItemEntity(
        id = item.getId(),
        index = offset + index,
        name = item.name!!,
    )
}