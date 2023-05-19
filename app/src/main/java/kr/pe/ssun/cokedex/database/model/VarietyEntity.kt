package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "variety",
)
data class VarietyEntity(
    @PrimaryKey @ColumnInfo(name = "v_id") val id: Int,
    @ColumnInfo(name = "s_id") val sId: Int,
)