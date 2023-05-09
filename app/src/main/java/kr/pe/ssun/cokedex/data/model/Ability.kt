package kr.pe.ssun.cokedex.data.model

import kr.pe.ssun.cokedex.database.model.AbilityEntity
import kr.pe.ssun.cokedex.network.model.NetworkAbility

fun NetworkAbility.asEntity() = AbilityEntity(
    id = id,
    name = getNameX(),
    nameLang = getNameLang(),
    flavor = getFlavor(),
    flavorLang = getFlavorLang(),
)