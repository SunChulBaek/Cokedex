package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Species

@Entity(
    tableName = "name"
)
data class SpeciesEntity(
    @PrimaryKey @ColumnInfo(name = "p_id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ec_id") val ecId: Int? = null,
)

fun SpeciesEntity.asExternalModel(fromDB: Boolean = false) = Species(
    id = id,
    name = name,
    ecId = ecId,
    fromDB = fromDB
)