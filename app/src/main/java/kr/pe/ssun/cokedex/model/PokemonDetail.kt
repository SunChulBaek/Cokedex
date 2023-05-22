package kr.pe.ssun.cokedex.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isDefault: Boolean,
    val weight: Int,
    val height: Int,
    val stats: List<Stat>,
    val varietyIds: List<Int>,
    // species
    val speciesId: Int, // for progress
    val species: Species?,
    // form
    val formId: Int, // for progress
    val form: Form?,
    // type
    val totalTypeIds: List<Int> = listOf(), // for progress
    val types: List<Type>,
    // Evolution Chain
    val evolutionChainId: Int?, // for progress
    val evolutionChain: EvolutionChain?,
)