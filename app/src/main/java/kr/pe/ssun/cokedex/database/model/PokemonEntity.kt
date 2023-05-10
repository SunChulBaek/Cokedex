package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import kr.pe.ssun.cokedex.model.Ability
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.model.PokemonStat
import kr.pe.ssun.cokedex.model.Type

@Entity(
    tableName = "pokemon"
)
data class PokemonEntity(
    @PrimaryKey @ColumnInfo(name = "p_id") val id: Int,
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