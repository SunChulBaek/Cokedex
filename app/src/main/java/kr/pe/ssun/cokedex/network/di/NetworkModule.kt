package kr.pe.ssun.cokedex.network.di

import kr.pe.ssun.cokedex.network.SsunNetworkDataSource
import kr.pe.ssun.cokedex.network.retrofit.RetrofitSsunNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SsunNetworkModule {
    @Binds
    fun RetrofitSsunNetwork.binds(): SsunNetworkDataSource
}