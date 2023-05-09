package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemon(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("base_experience") val baseExp: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("is_default") val isDefault: Boolean,
    @SerializedName("order") val order: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("abilities") val abilities: List<NetworkPokemonAbility>,
    @SerializedName("forms") val forms: List<NetworkNamedAPIResource> = listOf(),
    @SerializedName("game_indices") val gameIndices: List<NetworkVersionGameIndex> = listOf(),
    @SerializedName("held_items") val heldItems: List<NetworkPokemonHeldItem> = listOf(),
    @SerializedName("location_area_encounters") val locationAreaEncounters: String = "",
    @SerializedName("moves") val moves: List<NetworkPokemonMove>,
    @SerializedName("past_types") val pastTypes: List<NetworkPokemonTypePast> = listOf(),
    @SerializedName("sprites") val sprites: NetworkPokemonSprites? = null,
    @SerializedName("species") val species: NetworkNamedAPIResource? = null,
    @SerializedName("stats") val stats: List<NetworkPokemonStat> = listOf(),
    @SerializedName("types") val types: List<NetworkPokemonType>,
)