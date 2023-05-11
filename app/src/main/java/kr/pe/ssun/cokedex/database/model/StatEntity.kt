package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.model.PokemonStat

@Entity(
    tableName = "stat"
)
data class StatEntity(
    @PrimaryKey @ColumnInfo(name = "p_id") val id: Int,
    @ColumnInfo(name = "hp") val hp: Int,
    @ColumnInfo(name = "attack") val attack: Int,
    @ColumnInfo(name = "defense") val defense: Int,
    @ColumnInfo(name = "special_attack") val sAttack: Int,
    @ColumnInfo(name = "special_defense") val sDefense: Int,
    @ColumnInfo(name = "speed") val speed: Int,
)

fun StatEntity.asExternalModel(): List<PokemonStat> = listOf(
    PokemonStat("hp", hp),
    PokemonStat("attack", attack),
    PokemonStat("defense", defense),
    PokemonStat("special-attack", sAttack),
    PokemonStat("special-defense", sDefense),
    PokemonStat("speed", speed),
)