package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.TypeEntity
import java.util.Locale

data class NetworkType(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
//    @SerializedName("damage_relations") val damageRelations: TypeRelations,
//    @SerializedName("past_damage_relations") val pastDamageRelations: TypeRelationsPast,
//    @SerializedName("game_indices") val gameIndices: GenerationGameIndex,
//    @SerializedName("generation") val generation: NetworkNamedAPIResource,
//    @SerializedName("move_damage_class") val moveDamageClass: NetworkNamedAPIResource,
    @SerializedName("names") val names: List<NetworkName>,
//    @SerializedName("pokemon") val pokemon: TypePokemon,
//    @SerializedName("moves") val moves: List<NetworkNamedAPIResource>,
) {
    companion object {
        const val TYPE_NORMAL = 1
        const val TYPE_FIGHTING = 2
        const val TYPE_FLYING = 3
        const val TYPE_POISON = 4
        const val TYPE_GROUND = 5
        const val TYPE_ROCK = 6
        const val TYPE_BUG = 7
        const val TYPE_GHOST = 8
        const val TYPE_STEEL = 9
        const val TYPE_FIRE = 10
        const val TYPE_WATER = 11
        const val TYPE_GRASS = 12
        const val TYPE_ELECTRIC = 13
        const val TYPE_PSYCHIC = 14
        const val TYPE_ICE = 15
        const val TYPE_DRAGON = 16
        const val TYPE_DARK = 17
        const val TYPE_FAIRY = 18
        const val TYPE_UNKNOWN = 10001
        const val TYPE_SHADOW = 10002
    }
}

fun NetworkType.asEntity() = TypeEntity(
    id = id,
    name = names.firstOrNull {
        it.language.name == Locale.getDefault().language
    }?.name ?: names.firstOrNull {
        it.language.name == "en"
    }?.name
)