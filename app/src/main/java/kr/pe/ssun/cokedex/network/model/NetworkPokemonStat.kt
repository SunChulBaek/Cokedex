package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.StatEntity

// https://pokeapi.co/docs/v2#pokemonstat
data class NetworkPokemonStat(
    @SerializedName("base_stat") val baseStat: Int = 0,
    @SerializedName("effort") val effort: Int = 0,
    @SerializedName("stat") val stat: NetworkNamedAPIResource,
)

fun List<NetworkPokemonStat>.asEntity(pId: Int) = StatEntity(
    id = pId,
    hp = firstOrNull { it.stat.name == "hp" }?.baseStat ?: 0,
    attack = firstOrNull { it.stat.name == "attack" }?.baseStat ?: 0,
    defense = firstOrNull { it.stat.name == "defense" }?.baseStat ?: 0,
    sAttack = firstOrNull { it.stat.name == "special-attack" }?.baseStat ?: 0,
    sDefense = firstOrNull { it.stat.name == "special-defense" }?.baseStat ?: 0,
    speed = firstOrNull { it.stat.name == "speed" }?.baseStat ?: 0,
)