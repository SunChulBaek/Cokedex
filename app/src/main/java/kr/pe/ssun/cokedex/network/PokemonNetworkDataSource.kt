package kr.pe.ssun.cokedex.network

import kr.pe.ssun.cokedex.network.model.NetworkAPIResourceList
import kr.pe.ssun.cokedex.network.model.NetworkPokemonDetail

interface PokemonNetworkDataSource {
    suspend fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): NetworkAPIResourceList

    suspend fun getPokemonDetail(
        id: Int,
    ): NetworkPokemonDetail
}