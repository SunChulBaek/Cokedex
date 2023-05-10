package kr.pe.ssun.cokedex.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
    val abilities: List<Ability> = listOf(),
    val totalAbilityIds: List<Int> = listOf(),
    val moves: List<Ability> = listOf(),
    val totalMoveIds: List<Int> = listOf(),
    val weight: Int,
    val height: Int,
    val stats: List<PokemonStat>,
)