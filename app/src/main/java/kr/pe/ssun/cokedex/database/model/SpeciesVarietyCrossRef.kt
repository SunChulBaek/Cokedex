package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "p2v",
    primaryKeys = [ "s_id", "v_id" ],
)
data class SpeciesVarietyCrossRef(
    @ColumnInfo(name = "s_id") val sId: Int,
    @ColumnInfo(name = "v_id", index = true) val vId: Int,
)