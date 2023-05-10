package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Ability

@Entity(
    tableName = "move"
)
data class MoveEntity(
    @PrimaryKey  @ColumnInfo(name = "m_id")val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "nameLang") val nameLang: String?,
    @ColumnInfo(name = "flavor") val flavor: String?,
    @ColumnInfo(name = "flavorLang") val flavorLang: String?,
)

fun MoveEntity.asExternalModel(fromDB: Boolean = false) = Ability(
    id = id,
    name = name,
    flavor = flavor,
    fromDB = fromDB,
)