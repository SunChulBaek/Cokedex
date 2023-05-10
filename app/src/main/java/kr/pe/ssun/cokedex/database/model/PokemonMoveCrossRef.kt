package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "p2m",
    primaryKeys = [ "p_id", "m_id" ],
)
data class PokemonMoveCrossRef(
    @ColumnInfo(name = "p_id") val pId: Int,
    @ColumnInfo(name = "m_id", index = true) val mId: Int,
)