package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkChainLink(
    @SerializedName("is_baby") val isBaby: Boolean,
    @SerializedName("species") val species: NetworkNamedAPIResource,
    @SerializedName("evolution_details") val evolutionDetails: List<NetworkEvolutionDetail>,
    @SerializedName("evolves_to") val evolvesTo: List<NetworkChainLink>,
)