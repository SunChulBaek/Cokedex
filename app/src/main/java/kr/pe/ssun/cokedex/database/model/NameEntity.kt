package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Name

@Entity(
    tableName = "name"
)
data class NameEntity(
    @PrimaryKey @ColumnInfo(name = "p_id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
)

fun NameEntity.asExternalModel(fromDB: Boolean = false) = Name(
    id = id,
    name = name,
    fromDB = fromDB
)