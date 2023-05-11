package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "p2t",
    primaryKeys = [ "p_id", "t_id" ],
)
data class PokemonTypeCrossRef(
    @ColumnInfo(name = "p_id") val pId: Int,
    @ColumnInfo(name = "t_id", index = true) val tId: Int,
)