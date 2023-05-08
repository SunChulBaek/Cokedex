package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPokemonMoveVersion(
    @SerializedName("move_learn_method") val moveLearnMethod: NetworkNamedAPIResource,
    @SerializedName("version_group") val versionGroup: NetworkNamedAPIResource,
    @SerializedName("level_learned_at") val levelLearnedAt: Int,
)