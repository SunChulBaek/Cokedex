package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.LangValue
import kr.pe.ssun.cokedex.database.model.LangValueVersion
import kr.pe.ssun.cokedex.database.model.SpeciesEntity

data class NetworkPokemonSpecies(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("evolution_chain") val evolutionChain: NetworkAPIResource,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<NetworkFlavorText>,
    @SerializedName("varieties") val varieties: List<NetworkPokemonSpeciesVariety>,
) {
    fun getVarietyIds() = varieties.map { variety -> variety.getId() }
}

fun NetworkPokemonSpecies.asEntity() = SpeciesEntity(
    id = id,
    names =  names.map { LangValue(it.language.name!!, it.name) },
    flavorTexts = flavorTextEntries.map { LangValueVersion(it.language.name!!, it.flavorText, it.version.name!!) },
    ecId = evolutionChain.getId(),
    vIds = getVarietyIds(),
)