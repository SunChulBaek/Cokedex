package kr.pe.ssun.cokedex.data.model

data class UiPokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val colorStart: Long = 0x00000000,
    val colorEnd: Long = 0x00000000,
)