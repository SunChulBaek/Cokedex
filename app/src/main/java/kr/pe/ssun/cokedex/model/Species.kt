package kr.pe.ssun.cokedex.model

data class Species(
    override val id: Int,
    val name: String? = null,
    val ecId: Int? = null,
    override val fromDB: Boolean = false
) : Loadable(id, fromDB)