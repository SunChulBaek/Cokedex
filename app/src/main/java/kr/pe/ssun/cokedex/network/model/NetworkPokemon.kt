package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.data.model.UiAbility
import kr.pe.ssun.cokedex.data.model.UiPokemonDetail
import kr.pe.ssun.cokedex.data.model.UiPokemonStat
import kr.pe.ssun.cokedex.data.model.UiType

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

fun NetworkPokemon.asExternalPokemonModel() =
    UiPokemonDetail(
        id = this.id,
        name = this.name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        types = this.types.map { type ->
            UiType.fromValue(type.type.name)
        },
        abilities = this.abilities.map { ability ->
            UiAbility(
                id = ability.ability.url!!.split("/")[6].toInt(),
                name = ability.ability.name,
                flavor = ""
            )
        },
        totalAbilitiesCount = this.abilities.size,
        moves = this.moves.map { move ->
            UiAbility(
                id = move.move.url!!.split("/")[6].toInt(),
                name = move.move.name,
                flavor = ""
            )
        },
        totalMovesCount = this.moves.size,
        weight = this.weight,
        height = this.height,
        stats = this.stats.map { stat ->
            UiPokemonStat(stat.stat.name ?: "", stat.baseStat)
        }
    )