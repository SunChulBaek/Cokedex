package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.FlavorTextEntity
import kr.pe.ssun.cokedex.database.model.NameEntity
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
    name = names.getNameX() ?: name,
    flavorText = flavorTextEntries.getFlavorText(),
    ecId = evolutionChain.getId(),
    vIds = getVarietyIds(),
)

fun NetworkPokemonSpecies.asNameEntities() = names.map { name ->
    NameEntity(
        sId = this.id,
        lang = name.language.name!!,
        name = name.name
    )
}

fun NetworkPokemonSpecies.asFlavorTextEntities() = flavorTextEntries.map { flavorText ->
    FlavorTextEntity(
        sId = this.id,
        lang = flavorText.language.name!!,
        flavorText = flavorText.flavorText,
        version = flavorText.version.name!!,
    )
}