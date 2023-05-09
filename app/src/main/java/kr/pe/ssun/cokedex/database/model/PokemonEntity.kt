package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pe.ssun.cokedex.network.model.NetworkNamedAPIResource
import kr.pe.ssun.cokedex.network.model.NetworkPokemon
import kr.pe.ssun.cokedex.network.model.NetworkPokemonAbility
import kr.pe.ssun.cokedex.network.model.NetworkPokemonMove
import kr.pe.ssun.cokedex.network.model.NetworkPokemonStat
import kr.pe.ssun.cokedex.network.model.NetworkPokemonType

@Entity(
    tableName = "pokemon"
)
data class PokemonEntity(
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

// TODO : 수정 필요
fun PokemonEntity.asExternalModel() = NetworkPokemon(
    id = id,
    name = name ?: "",
    baseExp = baseExp,
    height = height,
    isDefault = isDefault,
    order = order,
    weight = weight,
    abilities = abilityIds.map { abilityId ->
        NetworkPokemonAbility(
            isHidden = false,
            slot = 0,
            ability = NetworkNamedAPIResource(
                name = "",
                url = "https://pokeapi.co/api/v2/ability/$abilityId/"
            )
        )
    },
    moves = moveIds.map { moveId ->
        NetworkPokemonMove(
            move = NetworkNamedAPIResource(
                name = "",
                url = "https://pokeapi.co/api/v2/move/$moveId/"
            )
        )
    },
    stats = stats.map { (name, value) ->
        NetworkPokemonStat(
            baseStat = value,
            stat = NetworkNamedAPIResource(
                name = name
            )
        )
    },
    types = types.map { type ->
        NetworkPokemonType(
            slot = 0,
            type = NetworkNamedAPIResource(
                name = type,
            )
        )
    }
)