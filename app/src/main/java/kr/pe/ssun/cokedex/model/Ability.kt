package kr.pe.ssun.cokedex.model

data class Ability(
    override val id: Int,
    val name: String? = null,
    val flavor: String? = null,
    override val fromDB: Boolean = false,
) : Loadable(id, fromDB)