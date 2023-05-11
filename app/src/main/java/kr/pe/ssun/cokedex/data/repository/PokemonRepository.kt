package kr.pe.ssun.cokedex.data.repository

import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.data.model.asEntity
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.model.FullPokemon
import kr.pe.ssun.cokedex.database.model.PokemonAbilityCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonMoveCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonTypeCrossRef
import kr.pe.ssun.cokedex.database.model.asExternalModel
import kr.pe.ssun.cokedex.model.Ability
import kr.pe.ssun.cokedex.model.Type
import kr.pe.ssun.cokedex.network.model.asEntity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val pokemonDao: PokemonDao,
    private val typeDao: TypeDao,
    private val abilityDao: AbilityDao,
    private val moveDao: MoveDao,
) {
    companion object {
        const val DUMMY_ID = 0
    }

    fun getType(id: Int): Flow<Type> = flow {
        typeDao.findById(id)?.let { type ->
            Timber.i("[sunchulbaek] Type(id = $id) DB에 저장되어 있음")
            emit(type.asExternalModel(fromDB = true))
        } ?: run {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] Type(id = 0) 은 무시")
                    emit(Type(DUMMY_ID))
                }
                else -> {
                    Timber.e("[sunchulbaek] Type(id = $id) DB에 저장되어 있지 않음. API 콜")
                    network.getType(id).asEntity().let { type ->
                        typeDao.insert(type)
                        emit(type.asExternalModel())
                    }
                }
            }
        }
    }

    fun getMove(id: Int): Flow<Ability> = flow {
        moveDao.findById(id)?.let { move ->
            Timber.i("[sunchulbaek] Move(id = $id) DB에 저장되어 있음")
            emit(move.asExternalModel(fromDB = true))
        } ?: run {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] Move(id = 0) 은 무시")
                    emit(Ability(DUMMY_ID))
                }
                else -> {
                    Timber.e("[sunchulbaek] Move(id = $id) DB에 저장되어 있지 않음. API 콜")
                    network.getMove(id).asEntity().let { move ->
                        moveDao.insert(move)
                        emit(move.asExternalModel())
                    }
                }
            }
        }
    }

    fun getAbility(id: Int): Flow<Ability> = flow {
        abilityDao.findById(id)?.let { ability ->
            Timber.i("[sunchulbaek] Ability(id = $id) DB에 저장되어 있음")
            emit(ability.asExternalModel(fromDB = true))
        } ?: run {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] Ability(id = 0) 은 무시")
                    emit(Ability(DUMMY_ID))
                }
                else -> {
                    Timber.e("[sunchulbaek] Ability(id = $id) DB에 저장되어 있지 않음. API 콜")
                    network.getAbility(id).asEntity().let { ability ->
                        abilityDao.insert(ability)
                        emit(ability.asExternalModel())
                    }
                }
            }
        }
    }

    fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): Flow<List<Pokemon>> = flow {
        emit(network.getPokemonList(
            limit = limit,
            offset = offset
        ).results.map { it.asExternalModel() })
    }

    fun getPokemonDetail(id: Int): Flow<PokemonDetail> = flow {
        pokemonDao.findById(id)?.let { fullPokemon ->
            Timber.i("[sunchulbaek] Pokemon(id =${id}) DB에 저장되어 있음")
            fullPokemon.types.forEach { type ->
                Timber.i("[sunchulbaek] Type(id = ${type.id}) DB에 저장되어 있음")
            }
            fullPokemon.abilities.forEach { ability ->
                Timber.i("[sunchulbaek] Ability(id = ${ability.id}) DB에 저장되어 있음")
            }
            fullPokemon.moves.forEach { move ->
                Timber.i("[sunchulbaek] Move(id = ${move.id}) DB에 저장되어 있음")
            }
            emit(fullPokemon.asExternalModel())
        } ?: run {
            Timber.e("[sunchulbaek] Pokemon(id =${id}) DB에 저장되어 있지 않음")
            network.getPokemonDetail(id).let { pokemon ->
                val entity = pokemon.asEntity()
                val stat = pokemon.stats.asEntity(pokemon.id)
                val entity2 = FullPokemon(
                    pokemon = pokemon.asEntity(),
                    abilities = abilityDao.findById(pokemon.getAbilityIds().toIntArray()),
                    moves = moveDao.findById(pokemon.getMoveIds().toIntArray()),
                    stat = stat,
                    types = typeDao.findById(pokemon.getTypeIds().toIntArray())
                )
                pokemonDao.insert(entity)
                pokemonDao.insert(stat)
                pokemon.types.forEach { type ->
                    pokemonDao.insert(PokemonTypeCrossRef(
                        pId = pokemon.id,
                        tId = type.getId()
                    ))
                }
                pokemon.abilities.forEach { ability ->
                    pokemonDao.insert(PokemonAbilityCrossRef(
                        pId = pokemon.id,
                        aId = ability.getId()
                    ))
                }
                pokemon.moves.forEach { move ->
                    pokemonDao.insert(PokemonMoveCrossRef(
                        pId = pokemon.id,
                        mId = move.getId()
                    ))
                }
                emit(entity2.asExternalModel())
            }
        }
    }
}