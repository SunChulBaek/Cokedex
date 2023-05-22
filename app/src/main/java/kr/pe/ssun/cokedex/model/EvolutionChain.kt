package kr.pe.ssun.cokedex.model

data class EvolutionChain(
    override val id: Int,
    val chains: List<List<Pair<Int, String>>> = listOf(),
    override val fromDB: Boolean = false
) : Loadable(id, fromDB)