package kr.pe.ssun.cokedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.pe.ssun.cokedex.database.dao.EvolutionChainDao
import kr.pe.ssun.cokedex.database.dao.SpeciesDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.PokemonItemDao
import kr.pe.ssun.cokedex.database.dao.StatDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.dao.ValueDao
import kr.pe.ssun.cokedex.database.model.EvolutionChainEntity
import kr.pe.ssun.cokedex.database.model.SpeciesEntity
import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.database.model.PokemonItemEntity
import kr.pe.ssun.cokedex.database.model.PokemonStatCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonTypeCrossRef
import kr.pe.ssun.cokedex.database.model.StatEntity
import kr.pe.ssun.cokedex.database.model.TypeEntity
import kr.pe.ssun.cokedex.database.model.ValueEntity
import kr.pe.ssun.cokedex.database.util.IdsConverter
import kr.pe.ssun.cokedex.database.util.StatsConverter
import kr.pe.ssun.cokedex.database.util.TypesConverter

@Database(
    entities = [
        PokemonItemEntity::class,
        PokemonEntity::class,
        SpeciesEntity::class,
        TypeEntity::class,
        StatEntity::class,
        ValueEntity::class,
        EvolutionChainEntity::class,
        PokemonStatCrossRef::class,
        PokemonTypeCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    IdsConverter::class,
    TypesConverter::class,
    StatsConverter::class,
)
abstract class CokedexDatabase : RoomDatabase() {
    abstract fun pokemonItemDao(): PokemonItemDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun speciesDao(): SpeciesDao
    abstract fun typeDao(): TypeDao
    abstract fun statDao(): StatDao
    abstract fun valueDao(): ValueDao
    abstract fun evolutionChainDao(): EvolutionChainDao
}