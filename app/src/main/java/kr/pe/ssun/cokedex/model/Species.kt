package kr.pe.ssun.cokedex.model

import kr.pe.ssun.cokedex.database.model.LangValue
import kr.pe.ssun.cokedex.database.model.LangValueVersion
import java.util.Locale

data class Species(
    override val id: Int,
    val names: List<LangValue> = listOf(),
    val flavorTexts: List<LangValueVersion> = listOf(),
    val ecId: Int? = null,
    val vIds: List<Int>? = null,
    override val fromDB: Boolean = false
) : Loadable(id, fromDB) {
    fun getName() = names.firstOrNull { name ->
        name.lang == Locale.getDefault().language
    }?.value ?: names.firstOrNull { name ->
        name.lang == "en"
    }?.value

    fun getFlavorText() = (flavorTexts.firstOrNull { name ->
        name.lang == Locale.getDefault().language
    }?.value ?: flavorTexts.firstOrNull { name ->
        name.lang == "en"
    }?.value)?.replace("\n", " ")
}