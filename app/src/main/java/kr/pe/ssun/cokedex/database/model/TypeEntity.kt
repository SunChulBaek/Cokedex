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
    @ColumnInfo(name = "names") val names: List<LangValue>,
)

fun TypeEntity.asExternalModel(fromDB: Boolean = false) = Type(
    id = id,
    names = names,
    fromDB = fromDB,
)