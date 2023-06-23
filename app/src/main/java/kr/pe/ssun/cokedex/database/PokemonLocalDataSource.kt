package kr.pe.ssun.cokedex.database

import kr.pe.ssun.cokedex.database.dao.EvolutionChainDao
import kr.pe.ssun.cokedex.database.dao.FormDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.PokemonItemDao
import kr.pe.ssun.cokedex.database.dao.SpeciesDao
import kr.pe.ssun.cokedex.database.dao.StatDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.dao.ValueDao
import kr.pe.ssun.cokedex.database.model.EvolutionChainEntity
import kr.pe.ssun.cokedex.database.model.FormEntity
import kr.pe.ssun.cokedex.database.model.FullPokemon
import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.database.model.PokemonItemEntity
import kr.pe.ssun.cokedex.database.model.PokemonStatCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonTypeCrossRef
import kr.pe.ssun.cokedex.database.model.SpeciesEntity
import kr.pe.ssun.cokedex.database.model.StatEntity
import kr.pe.ssun.cokedex.database.model.TypeEntity
import kr.pe.ssun.cokedex.database.model.ValueEntity
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val pokemonItemDao: PokemonItemDao,
    private val pokemonDao: PokemonDao,
    private val speciesDao: SpeciesDao,
    private val typeDao: TypeDao,
    private val statDao: StatDao,
    private val valueDao: ValueDao,
    private val evolutionChainDao: EvolutionChainDao,
    private val formDao: FormDao,
) {
    // Pokemon List
    suspend fun getPokemonItems(ids: List<Int> = listOf()): List<PokemonItemEntity> = when {
        ids.isNotEmpty() -> pokemonItemDao.findByIndex(ids.toIntArray())
        else -> pokemonItemDao.selectAll()
    }

    suspend fun insertPokemonItems(items: List<PokemonItemEntity>) = pokemonItemDao.insert(items)

    // Species
    suspend fun getSpecies(id: Int): SpeciesEntity? = speciesDao.findById(id)

    suspend fun insertSpecies(species: SpeciesEntity) = speciesDao.insert(species)

    // Type
    suspend fun getType(id: Int): TypeEntity? = typeDao.findById(id)

    suspend fun getTypes(ids: List<Int>): List<TypeEntity> = typeDao.findById(ids.toIntArray())

    suspend fun insertType(type: TypeEntity) = typeDao.insert(type)

    // Stat
    suspend fun getStat(id: Int): StatEntity? = statDao.findById(id)

    suspend fun insertStat(stat: StatEntity) = statDao.insert(stat)

    // Evolution Chain
    suspend fun getEvolutionChains(id: Int): List<EvolutionChainEntity> =
        evolutionChainDao.findById(id)

    suspend fun insertEvolutionChain(evolutionChain: EvolutionChainEntity) =
        evolutionChainDao.insert(evolutionChain)

    // Form
    suspend fun getForm(id: Int?): FormEntity? = formDao.findById(id)

    suspend fun insertForm(form: FormEntity) = formDao.insert(form)

    // Value
    suspend fun insertValue(value: ValueEntity) = valueDao.insert(value)

    // Pokemon Detail
    suspend fun getPokemon(id: Int): FullPokemon? = pokemonDao.findById(id)

    suspend fun insertPokemon(pokemon: PokemonEntity) = pokemonDao.insert(pokemon)

    suspend fun insertPokemonStatRef(p2s: PokemonStatCrossRef) = pokemonDao.insert(p2s)

    suspend fun insertPokemonTypeRef(p2t: PokemonTypeCrossRef) = pokemonDao.insert(p2t)
}