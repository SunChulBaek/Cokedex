package kr.pe.ssun.cokedex.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.serialization.gson.gson
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.NetworkAPIResourceList
import kr.pe.ssun.cokedex.network.model.NetworkAbility
import kr.pe.ssun.cokedex.network.model.NetworkEvolutionChain
import kr.pe.ssun.cokedex.network.model.NetworkMove
import kr.pe.ssun.cokedex.network.model.NetworkPokemon
import kr.pe.ssun.cokedex.network.model.NetworkPokemonForm
import kr.pe.ssun.cokedex.network.model.NetworkPokemonSpecies
import kr.pe.ssun.cokedex.network.model.NetworkStat
import kr.pe.ssun.cokedex.network.model.NetworkType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorSsunNetwork @Inject constructor() : PokemonNetworkDataSource {

    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            gson()
        }
        defaultRequest {
            url("https://pokeapi.co/api/v2/")
        }
    }

    override suspend fun getEvolutionChain(
        id: Int
    ): NetworkEvolutionChain = client.request("evolution-chain/$id").body()

    override suspend fun getSpecies(
        id: Int
    ): NetworkPokemonSpecies = client.request("pokemon-species/$id").body()

    override suspend fun getType(
        id: Int
    ): NetworkType = client.request("type/$id").body()

    override suspend fun getStat(
        id: Int
    ): NetworkStat = client.request("stat/$id").body()

    override suspend fun getMove(
        id: Int
    ): NetworkMove = client.request("move/$id").body()
    override suspend fun getAbility(
        id: Int,
    ): NetworkAbility = client.request("ability/$id").body()

    override suspend fun getForm(
        id: Int
    ): NetworkPokemonForm = client.request("pokemon-form/$id").body()

    override suspend fun getPokemonList(
        limit: Int?,
        offset: Int?,
    ): NetworkAPIResourceList = client.request("pokemon") {
        parameter("limit", limit)
        parameter("offset", offset)
    }.body()

    override suspend fun getPokemonDetail(
        id: Int
    ): NetworkPokemon = client.request("pokemon/$id").body()
}