package kr.pe.ssun.cokedex.model

import androidx.compose.ui.graphics.Color
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_BUG
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_DARK
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_DRAGON
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_ELECTRIC
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_FAIRY
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_FIGHTING
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_FIRE
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_FLYING
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_GRASS
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_ICE
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_NORMAL
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_POISON
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_PSYCHIC
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_ROCK
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_SHADOW
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_STEEL
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_WATER

data class Type(
    val id: Int,
    val name: String? = null,
    val fromDB: Boolean = false,
) {
    fun getColor(): Color = when (id) {
        TYPE_NORMAL -> Color(0xFF9E9E9E)
        TYPE_FIGHTING -> Color(0xFF9E9E9E)
        TYPE_FLYING -> Color(0xFF607D8B)
        TYPE_POISON -> Color(0xFF673AB7)
        TYPE_ROCK -> Color(0xFF9E9E9E)
        TYPE_BUG -> Color(0xFF4CAF50)
        TYPE_STEEL -> Color(0xFF9E9E9E)
        TYPE_FIRE -> Color(0xFFF44336)
        TYPE_WATER -> Color(0xFF3F51B5)
        TYPE_GRASS -> Color(0xFF4CAF50)
        TYPE_ELECTRIC -> Color(0xFFCDDC39)
        TYPE_PSYCHIC -> Color(0xFF9C27B0)
        TYPE_ICE -> Color(0xFF9E9E9E)
        TYPE_DRAGON -> Color(0xFF009688)
        TYPE_DARK -> Color(0xFF9E9E9E)
        TYPE_FAIRY -> Color(0xFF9E9E9E)
        TYPE_SHADOW -> Color(0xFF9E9E9E)
        else -> Color(0xFF9E9E9E)
    }
}