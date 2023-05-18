package kr.pe.ssun.cokedex.data.repository

import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.cokedex.data.model.EvolutionChains
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.data.model.asEntity
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.EvolutionChainDao
import kr.pe.ssun.cokedex.database.dao.SpeciesDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.PokemonItemDao
import kr.pe.ssun.cokedex.database.dao.StatDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.dao.ValueDao
import kr.pe.ssun.cokedex.database.model.FullPokemon
import kr.pe.ssun.cokedex.database.model.PokemonAbilityCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonMoveCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonStatCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonTypeCrossRef
import kr.pe.ssun.cokedex.database.model.ValueEntity
import kr.pe.ssun.cokedex.database.model.ValueWithStat
import kr.pe.ssun.cokedex.database.model.asExternalModel
import kr.pe.ssun.cokedex.model.Ability
import kr.pe.ssun.cokedex.model.Species
import kr.pe.ssun.cokedex.model.Stat
import kr.pe.ssun.cokedex.model.Type
import kr.pe.ssun.cokedex.network.model.asEntity
import kr.pe.ssun.cokedex.network.model.asNameEntity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val pokemonItemDao: PokemonItemDao,
    private val pokemonDao: PokemonDao,
    private val speciesDao: SpeciesDao,
    private val typeDao: TypeDao,
    private val statDao: StatDao,
    private val valueDao: ValueDao,
    private val abilityDao: AbilityDao,
    //private val moveDao: MoveDao,
    private val evolutionChainDao: EvolutionChainDao,
) {
    companion object {
        const val DUMMY_ID = 0
    }

    fun getSpecies(id: Int): Flow<Species> = flow {
        speciesDao.findById(id)?.let { species ->
            Timber.i("[sunchulbaek] Species(id = $id) DB에 저장되어 있음")
            emit(species.asExternalModel(fromDB = true))
        } ?: run {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] Species(id = 0) 은 무시")
                    emit(Species(DUMMY_ID))
                }
                else -> {
                    Timber.e("[sunchulbaek] Species(id = $id) DB에 저장되어 있지 않음. API 콜")
                    network.getSpecies(id).asNameEntity().let { name ->
                        speciesDao.insert(name)
                        emit(name.asExternalModel())
                    }
                }
            }
        }
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

    fun getStat(id: Int, value: Int): Flow<Stat> = flow {
        statDao.findById(id)?.let { stat ->
            Timber.i("[sunchulbaek] Stat(id = $id) DB에 저장되어 있음")
            emit(stat.asExternalModel(fromDB = true))
        } ?: run {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] Stat(id = 0) 은 무시")
                    emit(Stat(DUMMY_ID))
                }
                else -> {
                    Timber.e("[sunchulbaek] Stat(id = $id) DB에 저장되어 있지 않음. API 콜")
                    network.getStat(id).asEntity().let { stat ->
                        statDao.insert(stat)
                        emit(stat.asExternalModel(value))
                    }
                }
            }
        }
    }

    fun getMove(id: Int): Flow<Ability> = flow {
//        moveDao.findById(id)?.let { move ->
//            Timber.i("[sunchulbaek] Move(id = $id) DB에 저장되어 있음")
//            emit(move.asExternalModel(fromDB = true))
//        } ?: run {
//            when (id) {
//                DUMMY_ID -> {
//                    Timber.d("[sunchulbaek] Move(id = 0) 은 무시")
//                    emit(Ability(DUMMY_ID))
//                }
//                else -> {
//                    Timber.e("[sunchulbaek] Move(id = $id) DB에 저장되어 있지 않음. API 콜")
//                    network.getMove(id).asEntity().let { move ->
//                        moveDao.insert(move)
//                        emit(move.asExternalModel())
//                    }
//                }
//            }
//        }
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

    fun getEvolutionChain(id: Int): Flow<EvolutionChains> = flow {
        val entities = evolutionChainDao.findById(id)
        if (entities.isNotEmpty()) {
            Timber.i("[sunchulbaek] EvolutionChain(id = $id) DB에 저장되어 있음")
            emit(entities.asExternalModel())
        } else {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] EvolutionChain(id = 0) 은 무시")
                    emit(EvolutionChains())
                }

                else -> {
                    Timber.e("[sunchulbaek] EvolutionChain(id = $id) DB에 저장되어 있지 않음. API 콜")
                    val result = network.getEvolutionChain(id).asEntity()
                    result.forEach { chain ->
                        evolutionChainDao.insert(chain)
                    }
                    emit(result.asExternalModel())
                }
            }
        }
    }

    fun getPokemonList(limit: Int, offset: Int): Flow<List<Pokemon>> = flow {
        val indexes = (offset until offset + limit).toList().toIntArray()
        Timber.d("[sunchulbaek] Pokemon List limit = $limit, offset = $offset, ids = ${indexes.toList()}")
        val pokemonList = pokemonItemDao.findByIndex(indexes)
        if (pokemonList.isNotEmpty()) {
            Timber.i("[sunchulbaek] Pokemon List(offset = $offset, limit = $limit) DB에 저장되어 있음(size = ${pokemonList.size})")
            Timber.i("[sunchulbaek] Pokemon List = ${pokemonList.map { it.id }}")
            emit(pokemonList.asExternalModel())
        } else {
            Timber.e("[sunchulbaek] Pokemon List(offset = $offset, limit = $limit) DB에 저장되어 있지 않음")
            network.getPokemonList(limit = limit, offset = offset).asEntity(offset = offset).let { entities ->
                pokemonItemDao.insert(entities)
                emit(entities.asExternalModel())
            }
        }
    }

    fun getPokemonDetail(id: Int): Flow<PokemonDetail> = flow {
        pokemonDao.findById(id)?.let { fullPokemon ->
            Timber.i("[sunchulbaek] Pokemon(id =${id}) DB에 저장되어 있음")
            fullPokemon.types.forEach { type ->
                Timber.i("[sunchulbaek] Type(id = ${type.id}) DB에 저장되어 있음")
            }
            fullPokemon.stats.forEach { stat ->
                Timber.i("[sunchulbaek] Stat(sId = ${stat.value.sId}) DB에 저장되어 ${if (stat.stat != null) "있음" else "있지않음"}")
            }
            fullPokemon.abilities.forEach { ability ->
                Timber.i("[sunchulbaek] Ability(id = ${ability.id}) DB에 저장되어 있음")
            }
            fullPokemon.moves.forEach { move ->
                Timber.i("[sunchulbaek] Move(id = ${move.id}) DB에 저장되어 있음")
            }
            emit(fullPokemon.asExternalModel())
        } ?: run {
            Timber.e("[sunchulbaek] Pokemon(id = ${id}) DB에 저장되어 있지 않음")
            network.getPokemonDetail(id).let { pokemon ->
                val entity = pokemon.asEntity()
                val entity2 = FullPokemon(
                    pokemon = pokemon.asEntity(),
                    species = speciesDao.findById(pokemon.id),
                    abilities = abilityDao.findById(pokemon.getAbilityIds().toIntArray()),
                    moves = listOf(),//moveDao.findById(pokemon.getMoveIds().toIntArray()),
                    stats = pokemon.stats.map { stat ->
                        ValueWithStat(
                            value = ValueEntity(pokemon.id, stat.stat.getId(), stat.baseStat),
                            stat = statDao.findById(stat.stat.getId())
                        )
                    },
                    types = typeDao.findById(pokemon.getTypeIds().toIntArray())
                )
                pokemonDao.insert(entity)
                pokemon.stats.forEach { stat ->
                    pokemonDao.insert(PokemonStatCrossRef(
                        pId = pokemon.id,
                        sId = stat.stat.getId()
                    ))
                    valueDao.insert(ValueEntity(
                        pId = pokemon.id,
                        sId = stat.stat.getId(),
                        value = stat.baseStat
                    ))
                }
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