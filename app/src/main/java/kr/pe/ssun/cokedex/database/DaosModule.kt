package kr.pe.ssun.cokedex.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.pe.ssun.cokedex.database.dao.EvolutionChainDao
import kr.pe.ssun.cokedex.database.dao.FlavorTextDao
import kr.pe.ssun.cokedex.database.dao.FormDao
import kr.pe.ssun.cokedex.database.dao.NameDao
import kr.pe.ssun.cokedex.database.dao.SpeciesDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.PokemonItemDao
import kr.pe.ssun.cokedex.database.dao.StatDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.dao.ValueDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesPokemonItemDao(
        database: CokedexDatabase,
    ): PokemonItemDao = database.pokemonItemDao()

    @Provides
    fun providesPokemonDao(
        database: CokedexDatabase,
    ): PokemonDao = database.pokemonDao()

    @Provides
    fun providesSpeciesDao(
        database: CokedexDatabase,
    ): SpeciesDao = database.speciesDao()

    @Provides
    fun providesTypeDao(
        database: CokedexDatabase,
    ): TypeDao = database.typeDao()

    @Provides
    fun providesStatDao(
        database: CokedexDatabase,
    ): StatDao = database.statDao()

    @Provides
    fun providesValueDao(
        database: CokedexDatabase,
    ): ValueDao = database.valueDao()

    @Provides
    fun providesEvolutionChainDao(
        database: CokedexDatabase,
    ): EvolutionChainDao = database.evolutionChainDao()

    @Provides
    fun providesFormDao(
        database: CokedexDatabase,
    ): FormDao = database.formDao()

    @Provides
    fun providesNameDao(
        database: CokedexDatabase,
    ): NameDao = database.nameDao()

    @Provides
    fun proviesFlavorTextDao(
        database: CokedexDatabase
    ): FlavorTextDao = database.flavorTextDao()
}