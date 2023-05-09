package kr.pe.ssun.cokedex.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesPokemonDao(
        database: CokedexDatabase,
    ): PokemonDao = database.pokemonDao()

    @Provides
    fun providesAbilityDao(
        database: CokedexDatabase,
    ): AbilityDao = database.abilityDao()

    @Provides
    fun providesMoveDao(
        database: CokedexDatabase,
    ): MoveDao = database.moveDao()
}