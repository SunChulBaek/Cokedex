package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.ui.common.getImageUrl

@Entity(
    tableName = "pokemon_item"
)
data class PokemonItemEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("indexx") val index: Int,
    @ColumnInfo("name") val name: String,
)

fun List<PokemonItemEntity>.asExternalModel(fromDB: Boolean = false) = map { item ->
    Pokemon(
        id = item.id,
        name = item.name,
        fallbackName = item.name,
        imageUrl = getImageUrl(item.id),
        fromDB = fromDB,
    )
}