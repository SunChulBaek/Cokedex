package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

// https://pokeapi.co/docs/v2#pokemonstat
data class NetworkPokemonStat(
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("effort") val effort: Int,
    @SerializedName("stat") val stat: NetworkNamedAPIResource,
)