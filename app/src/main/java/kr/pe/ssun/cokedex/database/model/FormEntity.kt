package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Form

@Entity(
    tableName = "form"
)
data class FormEntity(
    @PrimaryKey @ColumnInfo("f_id") val id: Int,
    @ColumnInfo("name") val name: String?,
)

fun FormEntity.asExternalModel(fromDB: Boolean = false) = Form(
    id = id,
    name = name,
    fromDB = fromDB
)