package kr.pe.ssun.cokedex.data.model

import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.network.model.NetworkPokemon

fun NetworkPokemon.asEntity() = PokemonEntity(
    id = id,
    sId = species.getId(),
    name = name,
    baseExp = baseExp,
    height = height,
    isDefault = isDefault,
    order = order,
    weight = weight,
    typeIds = getTypeIds(),
)