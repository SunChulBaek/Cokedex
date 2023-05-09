package kr.pe.ssun.cokedex.data.model

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
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_UNKNOWN
import kr.pe.ssun.cokedex.network.model.NetworkType.Companion.TYPE_WATER

sealed interface UiType {

    companion object {
        fun fromValue(value: String?) = when (value) {
            TYPE_NORMAL -> Normal
            TYPE_FIGHTING -> Fighting
            TYPE_FLYING -> Flying
            TYPE_POISON -> Poison
            TYPE_ROCK -> Rock
            TYPE_BUG -> Bug
            TYPE_STEEL -> Steel
            TYPE_FIRE -> Fire
            TYPE_WATER -> Water
            TYPE_GRASS -> Grass
            TYPE_ELECTRIC -> Electric
            TYPE_PSYCHIC -> Psychic
            TYPE_ICE -> Ice
            TYPE_DRAGON -> Dragon
            TYPE_DARK -> Dark
            TYPE_FAIRY -> Fairy
            TYPE_SHADOW -> Shadow
            else -> Unknown
        }
    }

    fun getColor(): Color

    object Normal : UiType {
        override fun toString(): String = TYPE_NORMAL
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Fighting : UiType {
        override fun toString(): String = TYPE_FIGHTING
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Flying : UiType {
        override fun toString(): String = TYPE_FLYING
        override fun getColor(): Color = Color(0xFF607D8B)
    }

    object Poison : UiType {
        override fun toString(): String = TYPE_POISON
        override fun getColor(): Color = Color(0xFF673AB7)
    }

    object Rock : UiType {
        override fun toString(): String = TYPE_ROCK
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Bug : UiType {
        override fun toString(): String = TYPE_BUG
        override fun getColor(): Color = Color(0xFF4CAF50)
    }

    object Steel : UiType {
        override fun toString(): String = TYPE_STEEL
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Fire : UiType {
        override fun toString(): String = TYPE_FIRE
        override fun getColor(): Color = Color(0xFFF44336)
    }

    object Water : UiType {
        override fun toString(): String = TYPE_WATER
        override fun getColor(): Color = Color(0xFF3F51B5)
    }

    object Grass : UiType {
        override fun toString(): String = TYPE_GRASS
        override fun getColor(): Color = Color(0xFF4CAF50)
    }

    object Electric : UiType {
        override fun toString(): String = TYPE_ELECTRIC
        override fun getColor(): Color = Color(0xFFCDDC39)
    }

    object Psychic : UiType {
        override fun toString(): String = TYPE_PSYCHIC
        override fun getColor(): Color = Color(0xFF9C27B0)
    }

    object Ice : UiType {
        override fun toString(): String = TYPE_ICE
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Dragon : UiType {
        override fun toString(): String = TYPE_DRAGON
        override fun getColor(): Color = Color(0xFF009688)
    }

    object Dark : UiType {
        override fun toString(): String = TYPE_DARK
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Fairy : UiType {
        override fun toString(): String = TYPE_FAIRY
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Unknown : UiType {
        override fun toString(): String = TYPE_UNKNOWN
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }

    object Shadow : UiType {
        override fun toString(): String = TYPE_SHADOW
        override fun getColor(): Color = Color(0xFF9E9E9E)
    }
}