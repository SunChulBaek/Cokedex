package kr.pe.ssun.cokedex.model

data class Name(
    override val id: Int,
    val name: String? = null,
    override val fromDB: Boolean = false
) : Loadable(id, fromDB)