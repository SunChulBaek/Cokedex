package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

// https://pokeapi.co/docs/v2#pokemonstat
data class NetworkPokemonStat(
    @SerializedName("base_stat") val baseStat: Int = 0,
    @SerializedName("effort") val effort: Int = 0,
    @SerializedName("stat") val stat: NetworkNamedAPIResource,
)