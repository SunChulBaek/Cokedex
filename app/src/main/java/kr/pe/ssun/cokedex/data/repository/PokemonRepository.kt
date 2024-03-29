package kr.pe.ssun.cokedex.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kr.pe.ssun.cokedex.data.model.asEntity
import kr.pe.ssun.cokedex.database.PokemonLocalDataSource
import kr.pe.ssun.cokedex.database.model.*
import kr.pe.ssun.cokedex.model.*
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.asEntity
import kr.pe.ssun.cokedex.ui.common.getImageUrl
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val local: PokemonLocalDataSource,
    private val network: PokemonNetworkDataSource,
) {
    companion object {
        const val DUMMY_ID = 0
    }

    fun getSpecies(id: Int): Flow<Species> = flow {
        local.getSpecies(id)?.let { species ->
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
                        val entity = species.asEntity()

                        Timber.d("[sunchulbaek] Species(id = $id) varieties = ${species.getVarietyIds()}")
                        local.insertSpecies(entity)
                        emit(entity.asExternalModel())
                    }
                }
            }
        }
    }.flowOn(dispatcher)

    fun getType(id: Int): Flow<Type> = flow {
        local.getType(id)?.let { type ->
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
                        local.insertType(type)
                        emit(type.asExternalModel())
                    }
                }
            }
        }
    }.flowOn(dispatcher)

    fun getStat(id: Int, value: Int): Flow<Stat> = flow {
        local.getStat(id)?.let { stat ->
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
                        local.insertStat(stat)
                        emit(stat.asExternalModel(value))
                    }
                }
            }
        }
    }.flowOn(dispatcher)

    fun getEvolutionChain(id: Int): Flow<EvolutionChain> = flow {
        val entities = local.getEvolutionChains(id)
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
                        local.insertEvolutionChain(chain) // TODO
                    }
                    emit(result.asExternalModel())
                }
            }
        }
    }.flowOn(dispatcher)

    fun getForm(id: Int): Flow<Form> = flow {
        local.getForm(id)?.let { form ->
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
                        local.insertForm(form)
                        emit(form.asExternalModel())
                    }
                }
            }
        }
    }.flowOn(dispatcher)

    fun getPokemonList(limit: Int?, offset: Int?, search: String?): Flow<List<Pokemon>> = flow {
        Timber.d("[sunchulbaek] getPokemonList(limit = $limit, offset = $offset, search = $search)")
        if (limit == null && offset == null && search.isNullOrBlank()) {
            // 최초 로딩
            val cachedPokemonList = local.getPokemonItems()
            if (cachedPokemonList.isNotEmpty()) {
                Timber.d("[sunchulbaek] cache size = ${cachedPokemonList.size}")
                emit(cachedPokemonList.asExternalModel(fromDB = true))
                return@flow
            }
        } else if (limit == null && offset == null && !search.isNullOrBlank()) {
            // 검색
            Timber.d("[sunchulbaek] 검색!!!")
            val searchList = local.getAllSpecies().filter { species ->
                species.names.map { name ->
                    name.value.lowercase()
                }.any {
                    it.contains(search.lowercase())
                }.or(
                    species.id.toString().contains(search)
                )
            }
            Timber.d("[sunchulbaek] search size = ${searchList.size}")
            emit(searchList.map { item ->
                Pokemon(
                    id = item.id,
                    name = "name",
                    fallbackName = "name",
                    imageUrl = getImageUrl(item.id),
                    fromDB = true,
                ) })
            return@flow
        }

        val indexes = ((offset ?: 0) until (offset ?: 0) + (limit ?: 20)).toList()
        Timber.d("[sunchulbaek][onLoadMore] Pokemon List limit = $limit, offset = $offset, ids = ${indexes.toList()}")
        val pokemonList = local.getPokemonItems(indexes)
        if (pokemonList.isNotEmpty()) {
            Timber.i("[sunchulbaek] Pokemon List(offset = $offset, limit = $limit) DB에 저장되어 있음(size = ${pokemonList.size})")
            Timber.i("[sunchulbaek] Pokemon List = ${pokemonList.map { it.id }}")
            emit(pokemonList.asExternalModel(fromDB = true))
        } else {
            Timber.e("[sunchulbaek] Pokemon List(offset = $offset, limit = $limit) DB에 저장되어 있지 않음")
            network.getPokemonList(limit = limit, offset = offset).asEntity(offset = (offset ?: 0)).let { entities ->
                local.insertPokemonItems(entities)
                emit(entities.asExternalModel())
            }
        }
    }.flowOn(dispatcher)

    fun getPokemonDetail(id: Int): Flow<PokemonDetail> = flow {
        local.getPokemon(id)?.let { fullPokemon ->
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
                    species = local.getSpecies(pokemon.id),
                    stats = pokemon.stats.map { stat ->
                        ValueWithStat(
                            value = ValueEntity(pokemon.id, stat.stat.getId(), stat.baseStat),
                            stat = local.getStat(stat.stat.getId())
                        )
                    },
                    types = local.getTypes(pokemon.getTypeIds()),
                    form = local.getForm(pokemon.forms.firstOrNull()?.getId())
                )
                local.insertPokemon(entity)
                pokemon.stats.forEach { stat ->
                    local.insertPokemonStatRef(PokemonStatCrossRef(
                        pId = pokemon.id,
                        sId = stat.stat.getId()
                    ))
                    local.insertValue(ValueEntity(
                        pId = pokemon.id,
                        sId = stat.stat.getId(),
                        value = stat.baseStat
                    ))
                }
                pokemon.types.forEach { type ->
                    local.insertPokemonTypeRef(PokemonTypeCrossRef(
                        pId = pokemon.id,
                        tId = type.getId()
                    ))
                }
                emit(entity2.asExternalModel())
            }
        }
    }.flowOn(dispatcher)
}