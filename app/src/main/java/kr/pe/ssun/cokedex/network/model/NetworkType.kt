package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.LangValue
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
}

fun NetworkType.asEntity() = TypeEntity(
    id = id,
    names = names.map { name ->
        LangValue(name.language.name!!, name.name)
    }
)