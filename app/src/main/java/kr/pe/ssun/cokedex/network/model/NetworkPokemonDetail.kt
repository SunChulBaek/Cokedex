package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.data.model.PokemonDetail
import kr.pe.ssun.cokedex.data.model.PokemonStat
import kr.pe.ssun.cokedex.data.model.UiType

data class NetworkPokemonDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("base_experience") val baseExp: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("is_default") val isDefault: Boolean,
    @SerializedName("order") val order: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("types") val types: List<NetworkPokemonType>,
    @SerializedName("stats") val stats: List<NetworkPokemonStat>,
)

// https://pokeapi.co/docs/v2#pokemontype
data class NetworkPokemonType(
    @SerializedName("slot") val id: Int,
    @SerializedName("type") val type: NetworkNamedAPIResource,
)

// https://pokeapi.co/docs/v2#pokemonstat
data class NetworkPokemonStat(
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("effort") val effort: Int,
    @SerializedName("stat") val stat: NetworkNamedAPIResource,
)

fun NetworkPokemonDetail.asExternalModel() =
    PokemonDetail(
        id = this.id,
        name = this.name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        types = this.types.map { type ->
            UiType.fromValue(type.type.name)
        },
        weight = this.weight,
        height = this.height,
        stats = this.stats.map { stat ->
            PokemonStat(stat.stat.name, stat.baseStat)
        }
    )