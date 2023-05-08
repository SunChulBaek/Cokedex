package kr.pe.ssun.cokedex.data.repository

import kr.pe.ssun.cokedex.data.model.UiPokemon
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.asExternalPokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.cokedex.data.model.UiPokemonDetail
import kr.pe.ssun.cokedex.network.model.NetworkAbility
import kr.pe.ssun.cokedex.network.model.NetworkMove
import kr.pe.ssun.cokedex.network.model.NetworkNamedAPIResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource
) {

    fun getMove(
        id: Int,
    ): Flow<NetworkMove> = flow {
        emit(network.getMove(id))
    }

    fun getAbilities(
        id: Int,
    ): Flow<NetworkAbility> = flow {
        emit(network.getAbility(id))
    }

    fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): Flow<List<UiPokemon>> = flow {
        emit(network.getPokemonList(
            limit = limit,
            offset = offset
        ).results.map(NetworkNamedAPIResource::asExternalPokemonModel))
    }

    fun getPokemonDetail(
        id: Int,
    ): Flow<UiPokemonDetail> = flow {
        emit(network.getPokemonDetail(id).asExternalPokemonModel())
    }
}