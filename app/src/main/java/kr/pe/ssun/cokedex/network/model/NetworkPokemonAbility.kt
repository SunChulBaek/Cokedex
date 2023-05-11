package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemonAbility(
    @SerializedName("is_hidden") val isHidden: Boolean,
    @SerializedName("slot") val slot: Int,
    @SerializedName("ability") val ability: NetworkNamedAPIResource,
) {
    fun getId(): Int = ability.getId()
}