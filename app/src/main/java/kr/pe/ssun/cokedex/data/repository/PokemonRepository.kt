package kr.pe.ssun.cokedex.data.repository

import kr.pe.ssun.cokedex.data.model.UiPokemon
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.asExternalPokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.cokedex.data.model.UiPokemonDetail
import kr.pe.ssun.cokedex.data.model.asEntity
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.model.asExternalModel
import kr.pe.ssun.cokedex.network.model.NetworkAbility
import kr.pe.ssun.cokedex.network.model.NetworkMove
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val pokemonDao: PokemonDao,
    private val abilityDao: AbilityDao,
    private val moveDao: MoveDao,
) {

    fun getMove(id: Int): Flow<NetworkMove> = flow {
        moveDao.findById(id)?.let { move ->
            Timber.i("[sunchulbaek] Move(id = $id) DB에 저장되어 있음")
            emit(move.asExternalModel())
        } ?: run {
            Timber.e("[sunchulbaek] Move(id = $id) DB에 저장되어 있지 않음. API 콜")
            network.getMove(id).let { move ->
                moveDao.insert(move.asEntity())
                emit(move)
            }
        }
    }

    fun getAbility(id: Int): Flow<NetworkAbility> = flow {
        abilityDao.findById(id)?.let { ability ->
            Timber.i("[sunchulbaek] Ability(id = $id) DB에 저장되어 있음")
            emit(ability.asExternalModel())
        } ?: run {
            Timber.e("[sunchulbaek] Ability(id = $id) DB에 저장되어 있지 않음. API 콜")
            network.getAbility(id).let { ability ->
                abilityDao.insert(ability.asEntity())
                emit(ability)
            }
        }
    }

    fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): Flow<List<UiPokemon>> = flow {
        emit(network.getPokemonList(
            limit = limit,
            offset = offset
        ).results.map { it.asExternalPokemonModel()!! })
    }

    fun getPokemonDetail(id: Int): Flow<UiPokemonDetail> = flow {
        pokemonDao.findById(id)?.let { pokemon ->
            Timber.i("[sunchulbaek] Pokemon(id = $id) DB에 저장되어 있음")
            emit(pokemon.asExternalModel().asExternalPokemonModel())
        } ?: run {
            Timber.e("[sunchulbaek] Pokemon(id = $id) DB에 저장되어 있지 않음. API 콜")
            network.getPokemonDetail(id).let { pokemon ->
                pokemonDao.insert(pokemon.asEntity())
                emit(pokemon.asExternalPokemonModel())
            }
        }
    }
}