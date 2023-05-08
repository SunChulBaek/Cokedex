package kr.pe.ssun.cokedex.network.retrofit

import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.NetworkAPIResourceList
import kr.pe.ssun.cokedex.network.model.NetworkAbility
import kr.pe.ssun.cokedex.network.model.NetworkMove
import kr.pe.ssun.cokedex.network.model.NetworkPokemon
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface RetrofitSsunNetworkApi {

    @GET("move/{id}")
    suspend fun getMove(
        @Path("id") id: Int,
    ): NetworkMove

    @GET("ability/{id}")
    suspend fun getAbility(
        @Path("id") id: Int,
    ): NetworkAbility

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int? = 20,
        @Query("offset") offset: Int? = 0,
    ): NetworkAPIResourceList

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): NetworkPokemon
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

    override suspend fun getMove(
        id: Int
    ): NetworkMove = networkApi.getMove(id)
    override suspend fun getAbility(
        id: Int,
    ): NetworkAbility = networkApi.getAbility(id)

    override suspend fun getPokemonList(
        limit: Int?,
        offset: Int?,
    ): NetworkAPIResourceList = networkApi.getPokemonList(limit, offset)

    override suspend fun getPokemonDetail(
        id: Int
    ): NetworkPokemon = networkApi.getPokemonDetail(id)
}