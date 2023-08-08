package kr.pe.ssun.cokedex.di

import kr.pe.ssun.cokedex.network.ktor.KtorSsunNetwork
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kr.pe.ssun.cokedex.database.PokemonLocalDataSource
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesFakeRepository(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        local: PokemonLocalDataSource,
        apiService: KtorSsunNetwork,
    ): PokemonRepository {
        return PokemonRepository(
            dispatcher = dispatcher,
            local = local,
            network = apiService,
        )
    }
}