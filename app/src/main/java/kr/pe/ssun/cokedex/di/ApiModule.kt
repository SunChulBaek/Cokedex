package kr.pe.ssun.cokedex.di

import kr.pe.ssun.cokedex.BuildConfig
import kr.pe.ssun.cokedex.network.retrofit.RetrofitSsunNetwork
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kr.pe.ssun.cokedex.database.PokemonLocalDataSource
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
        local: PokemonLocalDataSource,
        apiService: RetrofitSsunNetwork,
    ): PokemonRepository {
        return PokemonRepository(
            dispatcher = dispatcher,
            local = local,
            network = apiService,
        )
    }
}