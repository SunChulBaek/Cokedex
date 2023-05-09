package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.network.model.NetworkAbilityFlavorText
import kr.pe.ssun.cokedex.network.model.NetworkMove
import kr.pe.ssun.cokedex.network.model.NetworkName
import kr.pe.ssun.cokedex.network.model.NetworkNamedAPIResource

@Entity(
    tableName = "move"
)
data class MoveEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "nameLang") val nameLang: String?,
    @ColumnInfo(name = "flavor") val flavor: String?,
    @ColumnInfo(name = "flavorLang") val flavorLang: String?,
)

// TODO : 수정 필요
fun MoveEntity.asExternalModel() = NetworkMove(
    id = id,
    names = listOf(
        NetworkName(
            name = name ?: "",
            language = NetworkNamedAPIResource(
                name = nameLang ?: "",
            )
        )
    ),
    flavorTextEntries = listOf(
        NetworkAbilityFlavorText(
            flavorText = flavor ?: "",
            language = NetworkNamedAPIResource(
                name = flavorLang ?: "",
            ),
        )
    )
)