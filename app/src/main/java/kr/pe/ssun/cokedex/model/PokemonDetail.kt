package kr.pe.ssun.cokedex.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val totalTypeIds: List<Int> = listOf(),
    val types: List<Type>,
    val weight: Int,
    val height: Int,
    val stats: List<Stat>,
    val evolutionChainId: Int?,
    val evolutionChains: List<List<Pair<Int, String>>> = listOf()
)