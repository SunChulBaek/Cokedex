package kr.pe.ssun.cokedex.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kr.pe.ssun.cokedex.data.model.asEntity
import kr.pe.ssun.cokedex.database.dao.*
import kr.pe.ssun.cokedex.database.model.*
import kr.pe.ssun.cokedex.model.*
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.asEntity
import kr.pe.ssun.cokedex.network.model.asNameEntity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: PokemonNetworkDataSource,
    private val pokemonItemDao: PokemonItemDao,
    private val pokemonDao: PokemonDao,
    private val speciesDao: SpeciesDao,
    private val typeDao: TypeDao,
    private val statDao: StatDao,
    private val valueDao: ValueDao,
    private val evolutionChainDao: EvolutionChainDao,
    private val formDao: FormDao,
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
                    Timber.d("[sunchulbaek] Species(id = $id) 은 무시")
                    emit(Species(DUMMY_ID))
                }
                else -> {
                    Timber.e("[sunchulbaek] Species(id = $id) DB에 저장되어 있지 않음. API 콜")
                    network.getSpecies(id).let { species ->
                        val entity = species.asNameEntity()

                        Timber.d("[sunchulbaek] Species(id = $id) varieties = ${species.getVarietyIds()}")
                        speciesDao.insert(entity)
                        emit(entity.asExternalModel())
                    }
                }
            }
        }
    }.flowOn(dispatcher)

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
    }.flowOn(dispatcher)

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
    }.flowOn(dispatcher)

    fun getEvolutionChain(id: Int): Flow<EvolutionChain> = flow {
        val entities = evolutionChainDao.findById(id)
        if (entities.isNotEmpty()) {
            Timber.i("[sunchulbaek] EvolutionChain(id = $id) DB에 저장되어 있음")
            emit(entities.asExternalModel(fromDB = true))
        } else {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] EvolutionChain(id = 0) 은 무시")
                    emit(EvolutionChain(DUMMY_ID))
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
    }.flowOn(dispatcher)

    fun getForm(id: Int): Flow<Form> = flow {
        formDao.findById(id)?.let { form ->
            Timber.i("[sunchulbaek] Form(id = $id) DB에 저장되어 있음")
            emit(form.asExternalModel(fromDB = true))
        } ?: run {
            when (id) {
                DUMMY_ID -> {
                    Timber.d("[sunchulbaek] Form(id = 0) 은 무시")
                    emit(Form(DUMMY_ID))
                }
                else -> {
                    Timber.e("[sunchulbaek] Stat(id = $id) DB에 저장되어 있지 않음. API 콜")
                    network.getForm(id).asEntity().let { form ->
                        formDao.insert(form)
                        emit(form.asExternalModel())
                    }
                }
            }
        }
    }.flowOn(dispatcher)

    fun getPokemonList(limit: Int?, offset: Int?): Flow<List<Pokemon>> = flow {
        if (limit == null && offset == null) {
            val cachedPokemonList = pokemonItemDao.selectAll()
            if (cachedPokemonList.isNotEmpty()) {
                Timber.d("[sunchulbaek] cache size = ${cachedPokemonList.size}")
                emit(cachedPokemonList.asExternalModel(fromDB = true))
                return@flow
            }
        }
        val indexes = ((offset ?: 0) until (offset ?: 0) + (limit ?: 20)).toList().toIntArray()
        Timber.d("[sunchulbaek][onLoadMore] Pokemon List limit = $limit, offset = $offset, ids = ${indexes.toList()}")
        val pokemonList = pokemonItemDao.findByIndex(indexes)
        if (pokemonList.isNotEmpty()) {
            Timber.i("[sunchulbaek] Pokemon List(offset = $offset, limit = $limit) DB에 저장되어 있음(size = ${pokemonList.size})")
            Timber.i("[sunchulbaek] Pokemon List = ${pokemonList.map { it.id }}")
            emit(pokemonList.asExternalModel(fromDB = true))
        } else {
            Timber.e("[sunchulbaek] Pokemon List(offset = $offset, limit = $limit) DB에 저장되어 있지 않음")
            network.getPokemonList(limit = limit, offset = offset).asEntity(offset = (offset ?: 0)).let { entities ->
                pokemonItemDao.insert(entities)
                emit(entities.asExternalModel())
            }
        }
    }.flowOn(dispatcher)

    fun getPokemonDetail(id: Int): Flow<PokemonDetail> = flow {
        pokemonDao.findById(id)?.let { fullPokemon ->
            Timber.i("[sunchulbaek] Pokemon(id =${id}) DB에 저장되어 있음")
            fullPokemon.types.forEach { type ->
                Timber.i("[sunchulbaek] Type(id = ${type.id}) DB에 저장되어 있음")
            }
            emit(fullPokemon.asExternalModel())
        } ?: run {
            Timber.e("[sunchulbaek] Pokemon(id = ${id}) DB에 저장되어 있지 않음")
            network.getPokemonDetail(id).let { pokemon ->
                val entity = pokemon.asEntity()
                val entity2 = FullPokemon(
                    pokemon = entity,
                    species = speciesDao.findById(pokemon.id),
                    stats = pokemon.stats.map { stat ->
                        ValueWithStat(
                            value = ValueEntity(pokemon.id, stat.stat.getId(), stat.baseStat),
                            stat = statDao.findById(stat.stat.getId())
                        )
                    },
                    types = typeDao.findById(pokemon.getTypeIds().toIntArray()),
                    form = formDao.findById(pokemon.forms.firstOrNull()?.getId())
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
                emit(entity2.asExternalModel())
            }
        }
    }.flowOn(dispatcher)
}