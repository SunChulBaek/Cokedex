package kr.pe.ssun.cokedex.data.model

import kr.pe.ssun.cokedex.database.model.MoveEntity
import kr.pe.ssun.cokedex.network.model.NetworkMove

fun NetworkMove.asEntity() = MoveEntity(
    id = id,
    name = getName(),
    nameLang = getNameLang(),
    flavor = getFlavor(),
    flavorLang = getFlavorLang(),
)