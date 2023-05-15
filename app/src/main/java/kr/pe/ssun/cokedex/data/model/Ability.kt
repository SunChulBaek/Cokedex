package kr.pe.ssun.cokedex.data.model

import kr.pe.ssun.cokedex.database.model.AbilityEntity
import kr.pe.ssun.cokedex.network.model.NetworkAbility
import kr.pe.ssun.cokedex.network.model.getNameLang
import kr.pe.ssun.cokedex.network.model.getNameX

fun NetworkAbility.asEntity() = AbilityEntity(
    id = id,
    name = names.getNameX(),
    nameLang = names.getNameLang(),
    flavor = getFlavor(),
    flavorLang = getFlavorLang(),
)