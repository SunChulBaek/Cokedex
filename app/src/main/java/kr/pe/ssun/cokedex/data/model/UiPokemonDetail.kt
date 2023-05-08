package kr.pe.ssun.cokedex.data.model

data class UiPokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<UiType>,
    val abilities: List<UiAbility>,
    val totalAbilitiesCount: Int,
    val moves: List<UiAbility>,
    val totalMovesCount: Int,
    val weight: Int,
    val height: Int,
    val stats: List<UiPokemonStat>,
)

data class UiPokemonStat(
    val name: String,
    val value: Int,
)