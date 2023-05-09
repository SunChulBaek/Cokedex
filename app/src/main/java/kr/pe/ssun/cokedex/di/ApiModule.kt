package kr.pe.ssun.cokedex.di

import kr.pe.ssun.cokedex.BuildConfig
import kr.pe.ssun.cokedex.network.retrofit.RetrofitSsunNetwork
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
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
        apiService: RetrofitSsunNetwork,
        pokemonDao: PokemonDao,
        abilityDao: AbilityDao,
        moveDao: MoveDao,
    ): PokemonRepository {
        return PokemonRepository(apiService, pokemonDao, abilityDao, moveDao)
    }
}