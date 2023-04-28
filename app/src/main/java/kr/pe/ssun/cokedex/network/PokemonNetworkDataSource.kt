package kr.pe.ssun.cokedex.network

import kr.pe.ssun.cokedex.network.model.NetworkPokemonDetail
import kr.pe.ssun.cokedex.network.model.NetworkPokemonList

interface PokemonNetworkDataSource {
    suspend fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): NetworkPokemonList

    suspend fun getPokemonDetail(
        id: Int,
    ): NetworkPokemonDetail
}