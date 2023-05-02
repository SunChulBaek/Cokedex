package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName

data class NetworkType(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
//    @SerializedName("damage_relations") val damageRelations: TypeRelations,
//    @SerializedName("past_damage_relations") val pastDamageRelations: TypeRelationsPast,
//    @SerializedName("game_indices") val gameIndices: GenerationGameIndex,
    @SerializedName("generation") val generation: NetworkNamedAPIResource,
    @SerializedName("move_damage_class") val moveDamageClass: NetworkNamedAPIResource,
//    @SerializedName("names") val names: List<Name>,
//    @SerializedName("pokemon") val pokemon: TypePokemon,
    @SerializedName("moves") val moves: List<NetworkNamedAPIResource>,
) {
    companion object {
        const val TYPE_NORMAL = "normal"
        const val TYPE_FIGHTING = "fighting"
        const val TYPE_FLYING = "flying"
        const val TYPE_POISON = "poison"
        const val TYPE_GROUND = "ground"
        const val TYPE_ROCK = "rock"
        const val TYPE_BUG = "bug"
        const val TYPE_GHOST = "ghost"
        const val TYPE_STEEL = "steel"
        const val TYPE_FIRE = "fire"
        const val TYPE_WATER = "water"
        const val TYPE_GRASS = "grass"
        const val TYPE_ELECTRIC = "electric"
        const val TYPE_PSYCHIC = "psychic"
        const val TYPE_ICE = "ice"
        const val TYPE_DRAGON = "dragon"
        const val TYPE_DARK = "dark"
        const val TYPE_FAIRY = "fairy"
        const val TYPE_UNKNOWN = "unknown"
        const val TYPE_SHADOW = "shadow"
    }
}