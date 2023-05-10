package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "p2a",
    primaryKeys = [ "p_id", "a_id" ],
)
data class PokemonAbilityCrossRef(
    @ColumnInfo(name = "p_id") val pId: Int,
    @ColumnInfo(name = "a_id", index = true) val aId: Int,
)