package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Ability
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.model.PokemonStat
import kr.pe.ssun.cokedex.model.Type

@Entity(
    tableName = "pokemon"
)
data class PokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "base_experience") val baseExp: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "is_default") val isDefault: Boolean,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "abilityIds") val abilityIds: List<Int>,
    @ColumnInfo(name = "moveIds") val moveIds: List<Int>,
    @ColumnInfo(name = "stats") val stats: List<Pair<String, Int>>,
    @ColumnInfo(name = "types") val types: List<String>,
)

fun PokemonEntity.asExternalModel() = PokemonDetail(
    id = id,
    name = name ?: "",
    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
    types = types.map { type ->
        Type.fromValue(type)
    },
    abilities = abilityIds.map { abilityId ->
        Ability(
            id = abilityId,
            name = "",
            flavor = "",
        )
    },
    totalAbilitiesCount = abilityIds.size,
    moves = moveIds.map { moveId ->
        Ability(
            id = moveId,
            name = "",
            flavor = "",
        )
    },
    totalMovesCount = moveIds.size,
    weight = weight,
    height = height,
    stats = stats.map { (name, value) ->
        PokemonStat(name = name, value = value)
    }
)