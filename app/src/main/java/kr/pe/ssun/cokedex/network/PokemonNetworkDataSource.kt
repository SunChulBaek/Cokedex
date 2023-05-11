package kr.pe.ssun.cokedex.network

import kr.pe.ssun.cokedex.network.model.NetworkAPIResourceList
import kr.pe.ssun.cokedex.network.model.NetworkAbility
import kr.pe.ssun.cokedex.network.model.NetworkMove
import kr.pe.ssun.cokedex.network.model.NetworkPokemon
import kr.pe.ssun.cokedex.network.model.NetworkType

interface PokemonNetworkDataSource {

    suspend fun getType(
        id: Int,
    ): NetworkType

    suspend fun getMove(
        id: Int,
    ): NetworkMove

    suspend fun getAbility(
        id: Int,
    ): NetworkAbility

    suspend fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): NetworkAPIResourceList

    suspend fun getPokemonDetail(
        id: Int,
    ): NetworkPokemon
}