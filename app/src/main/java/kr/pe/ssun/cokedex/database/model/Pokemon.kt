package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "base_experience") val baseExp: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "is_default") val isDefault: Boolean,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "abilityIds") val abilityIds: List<Int>,
    @ColumnInfo(name = "moveIds") val moveIds: List<Int>,
    @ColumnInfo(name = "stats") val stats: List<Pair<String, Int>>,
    @ColumnInfo(name = "types") val types: List<String>,
)