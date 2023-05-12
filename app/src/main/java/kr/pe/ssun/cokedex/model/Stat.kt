package kr.pe.ssun.cokedex.model

data class Stat(
    override val id: Int,
    val name: String? = null,
    val value: Int? = null,
    override val fromDB: Boolean = false,
) : Loadable(id, fromDB)