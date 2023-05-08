package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAbilityPokemon(
    @SerializedName("is_hidden") val isHidden: Boolean,
    @SerializedName("slot") val slot: Int,
    @SerializedName("pokemon") val pokemon: NetworkNamedAPIResource,
)