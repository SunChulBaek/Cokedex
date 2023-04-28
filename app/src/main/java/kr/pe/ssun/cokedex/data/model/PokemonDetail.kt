package kr.pe.ssun.cokedex.data.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
)

data class PokemonType(
    val name: String
)

data class PokemonStat(
    val name: String,
    val value: Int,
)