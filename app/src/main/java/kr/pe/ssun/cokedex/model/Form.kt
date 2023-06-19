package kr.pe.ssun.cokedex.model

import kr.pe.ssun.cokedex.database.model.LangValue
import java.util.Locale

data class Form(
    override val id: Int,
    val names: List<LangValue> = listOf(),
    override val fromDB: Boolean = false,
) : Loadable(id, fromDB) {
    fun getName() = names.firstOrNull { name ->
        name.lang == Locale.getDefault().language
    }?.value ?: names.firstOrNull { name ->
        name.lang == "en"
    }?.value
}