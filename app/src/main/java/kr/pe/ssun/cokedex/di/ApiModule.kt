package kr.pe.ssun.cokedex.di

import kr.pe.ssun.cokedex.BuildConfig
import kr.pe.ssun.cokedex.network.retrofit.RetrofitSsunNetwork
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kr.pe.ssun.cokedex.database.dao.EvolutionChainDao
import kr.pe.ssun.cokedex.database.dao.FormDao
import kr.pe.ssun.cokedex.database.dao.SpeciesDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.dao.PokemonItemDao
import kr.pe.ssun.cokedex.database.dao.StatDao
import kr.pe.ssun.cokedex.database.dao.TypeDao
import kr.pe.ssun.cokedex.database.dao.ValueDao
import kr.pe.ssun.cokedex.util.IoDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
        }
        .build()
    }

    @Singleton
    @Provides
    fun providesFakeRepository(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        apiService: RetrofitSsunNetwork,
        pokemonItemDao: PokemonItemDao,
        pokemonDao: PokemonDao,
        nameDao: SpeciesDao,
        typeDao: TypeDao,
        statDao: StatDao,
        valueDao: ValueDao,
        evolutionChainDao: EvolutionChainDao,
        formDao: FormDao,
    ): PokemonRepository {
        return PokemonRepository(
            dispatcher = dispatcher,
            network = apiService,
            pokemonItemDao = pokemonItemDao,
            pokemonDao = pokemonDao,
            speciesDao = nameDao,
            typeDao = typeDao,
            statDao = statDao,
            valueDao = valueDao,
            evolutionChainDao = evolutionChainDao,
            formDao = formDao,
        )
    }
}