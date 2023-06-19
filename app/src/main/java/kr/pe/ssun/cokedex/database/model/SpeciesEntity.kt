package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Species

@Entity(
    tableName = "species"
)
data class SpeciesEntity(
    @PrimaryKey @ColumnInfo(name = "s_id") val id: Int,
    @ColumnInfo(name = "names") val names: List<LangValue>,
    @ColumnInfo(name = "flavor_texts") val flavorTexts: List<LangValueVersion>,
    @ColumnInfo(name = "ec_id") val ecId: Int? = null,
    @ColumnInfo(name = "v_ids") val vIds: List<Int>? = null
)

data class LangValue(
    val lang: String,
    val value: String,
)

data class LangValueVersion(
    val lang: String,
    val value: String,
    val version: String,
)

fun SpeciesEntity.asExternalModel(fromDB: Boolean = false) = Species(
    id = id,
    names = names,
    flavorTexts = flavorTexts,
    ecId = ecId,
    vIds = vIds,
    fromDB = fromDB
)