package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon"
)
data class PokemonEntity(
    @PrimaryKey @ColumnInfo(name = "p_id") val id: Int,
    @ColumnInfo(name = "s_id") val sId: Int,
    @ColumnInfo(name = "f_id") val fId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "base_experience") val baseExp: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "is_default") val isDefault: Boolean,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "typeIds") val typeIds: List<Int>,
)