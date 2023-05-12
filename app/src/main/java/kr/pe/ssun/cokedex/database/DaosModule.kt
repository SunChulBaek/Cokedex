package kr.pe.ssun.cokedex.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.StatDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.dao.ValueDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesPokemonDao(
        database: CokedexDatabase,
    ): PokemonDao = database.pokemonDao()

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
    fun providesAbilityDao(
        database: CokedexDatabase,
    ): AbilityDao = database.abilityDao()

    @Provides
    fun providesMoveDao(
        database: CokedexDatabase,
    ): MoveDao = database.moveDao()
}