package kr.pe.ssun.cokedex.data.model

data class UiPokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val colorStart: Int = 0x00000000,
    val colorEnd: Int = 0x00000000,
)