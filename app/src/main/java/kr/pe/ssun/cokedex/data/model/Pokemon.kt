package kr.pe.ssun.cokedex.data.model

import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.network.model.NetworkPokemon

fun NetworkPokemon.asEntity() = PokemonEntity(
    id = id,
    name = name,
    baseExp = baseExp,
    height = height,
    isDefault = isDefault,
    order = order,
    weight = weight,
    abilityIds = abilities.map {
        it.ability.url!!.split("/")[6].toInt()
    },
    moveIds = moves.map {
        it.move.url!!.split("/")[6].toInt()
    },
    stats = stats.map {
        Pair(it.stat.name!!, it.baseStat)
    },
    types = types.map {
        it.type.name!!
    }
)