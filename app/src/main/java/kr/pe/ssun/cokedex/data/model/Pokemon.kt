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
    abilityIds = abilities.map { ability -> ability.ability.getId() },
    moveIds = moves.map { move -> move.move.getId() },
    types = types.map {
        it.type.name!!
    }
)