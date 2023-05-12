package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.PokemonStat

@Entity(
    tableName = "stat"
)
data class StatEntity(
    @PrimaryKey @ColumnInfo(name = "s_id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
)

fun StatEntity.asExternalModel(value: Int = 0, fromDB: Boolean = false) = PokemonStat(
    id = id,
    name = name,
    value = value,
    fromDB = fromDB
)