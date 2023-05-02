package kr.pe.ssun.cokedex.data.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<UiType>,
    val weight: Int,
    val height: Int,
    val stats: List<PokemonStat>,
)

data class PokemonStat(
    val name: String,
    val value: Int,
)