package kr.pe.ssun.cokedex.network.di

import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.ktor.KtorSsunNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SsunNetworkModule {
    @Binds
    fun bindKtorSsunNetwork(
        network: KtorSsunNetwork
    ): PokemonNetworkDataSource
}