package kr.pe.ssun.cokedex.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
    val abilities: List<Ability>,
    val totalAbilitiesCount: Int,
    val moves: List<Ability>,
    val totalMovesCount: Int,
    val weight: Int,
    val height: Int,
    val stats: List<PokemonStat>,
)