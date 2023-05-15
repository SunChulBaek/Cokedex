package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.NameEntity

data class NetworkPokemonSpecies(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("names") val names: List<NetworkName>,
)

fun NetworkPokemonSpecies.asNameEntity() = NameEntity(
    id = id,
    name = names.getNameX() ?: name
)