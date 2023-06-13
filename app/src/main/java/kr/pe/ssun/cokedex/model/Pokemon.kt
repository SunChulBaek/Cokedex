package kr.pe.ssun.cokedex.model

data class Pokemon(
    override val id: Int,
    val name: String,
    val fallbackName: String,
    val imageUrl: String,
    val colorStart: Int = 0x00000000,
    val colorEnd: Int = 0x00000000,
    override val fromDB: Boolean = false,
) : Loadable(id, fromDB)