package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "p2s",
    primaryKeys = [ "p_id", "s_id" ],
)
data class PokemonStatCrossRef(
    @ColumnInfo(name = "p_id") val pId: Int,
    @ColumnInfo(name = "s_id", index = true) val sId: Int,
)