package kr.pe.ssun.cokedex.model

data class Ability(
    val id: Int,
    val name: String? = null,
    val flavor: String? = null,
    val fromDB: Boolean = false,
)