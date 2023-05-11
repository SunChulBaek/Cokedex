package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Type

@Entity(
    tableName = "type"
)
data class TypeEntity(
    @PrimaryKey @ColumnInfo(name = "t_id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
)

fun TypeEntity.asExternalModel(fromDB: Boolean = false) = Type(
    id = id,
    name = name,
    fromDB = fromDB,
)