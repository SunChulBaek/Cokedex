package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "name",
    primaryKeys = [ "s_id", "language" ],
)
data class NameEntity(
    @ColumnInfo(name = "s_id") val sId: Int,
    @ColumnInfo(name = "language") val lang: String,
    @ColumnInfo(name = "name") val name: String,
)