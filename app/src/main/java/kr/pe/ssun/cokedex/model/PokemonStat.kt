package kr.pe.ssun.cokedex.model

data class PokemonStat(
    val id: Int,
    val name: String? = null,
    val value: Int? = null,
    val fromDB: Boolean = false,
)