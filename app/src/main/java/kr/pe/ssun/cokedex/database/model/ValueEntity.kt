package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "value",
    primaryKeys = [ "p_id", "s_id" ]
)
data class ValueEntity(
    @ColumnInfo(name = "p_id") val pId: Int,
    @ColumnInfo(name = "s_id") val sId: Int,
    @ColumnInfo(name = "value") val value: Int,
)