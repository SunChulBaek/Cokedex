package kr.pe.ssun.cokedex.data.repository

import kr.pe.ssun.cokedex.data.model.Pokemon
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.NetworkPokemon
import kr.pe.ssun.cokedex.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.cokedex.data.model.PokemonDetail
import kr.pe.ssun.cokedex.network.model.NetworkPokemonDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource
) {

    fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): Flow<List<Pokemon>> = flow {
        emit(network.getPokemonList(
            limit = limit,
            offset = offset
        ).results.map(NetworkPokemon::asExternalModel))
    }

    fun getPokemonDetail(
        id: Int,
    ): Flow<PokemonDetail> = flow {
        emit(network.getPokemonDetail(id).asExternalModel())
    }
}