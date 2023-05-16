package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.SpeciesEntity

data class NetworkPokemonSpecies(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("evolution_chain") val evolutionChain: NetworkAPIResource
)

fun NetworkPokemonSpecies.asNameEntity() = SpeciesEntity(
    id = id,
    name = names.getNameX() ?: name,
    ecId = evolutionChain.getId()
)