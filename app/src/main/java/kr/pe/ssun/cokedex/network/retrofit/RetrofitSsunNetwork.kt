package kr.pe.ssun.cokedex.network.retrofit

import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.NetworkPokemon
import kr.pe.ssun.cokedex.network.model.NetworkPokemonList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface RetrofitSsunNetworkApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int? = 20,
        @Query("offset") offset: Int? = 0,
    ): NetworkPokemonList
}

@Singleton
class RetrofitSsunNetwork @Inject constructor() : PokemonNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    // TODO: Decide logging logic
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitSsunNetworkApi::class.java)

    override suspend fun getPokemonList(
        limit: Int?,
        offset: Int?,
    ): NetworkPokemonList = networkApi.getPokemonList(limit, offset)
}