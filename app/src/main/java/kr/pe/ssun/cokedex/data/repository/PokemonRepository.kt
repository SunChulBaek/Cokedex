package kr.pe.ssun.cokedex.data.repository

import kr.pe.ssun.cokedex.data.model.UiPokemon
import kr.pe.ssun.cokedex.network.PokemonNetworkDataSource
import kr.pe.ssun.cokedex.network.model.asExternalPokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.pe.ssun.cokedex.data.model.UiPokemonDetail
import kr.pe.ssun.cokedex.database.dao.AbilityDao
import kr.pe.ssun.cokedex.database.dao.MoveDao
import kr.pe.ssun.cokedex.database.dao.PokemonDao
import kr.pe.ssun.cokedex.database.model.Ability
import kr.pe.ssun.cokedex.database.model.Move
import kr.pe.ssun.cokedex.database.model.Pokemon
import kr.pe.ssun.cokedex.network.model.NetworkAbility
import kr.pe.ssun.cokedex.network.model.NetworkAbilityFlavorText
import kr.pe.ssun.cokedex.network.model.NetworkMove
import kr.pe.ssun.cokedex.network.model.NetworkName
import kr.pe.ssun.cokedex.network.model.NetworkNamedAPIResource
import kr.pe.ssun.cokedex.network.model.NetworkPokemon
import kr.pe.ssun.cokedex.network.model.NetworkPokemonAbility
import kr.pe.ssun.cokedex.network.model.NetworkPokemonMove
import kr.pe.ssun.cokedex.network.model.NetworkPokemonStat
import kr.pe.ssun.cokedex.network.model.NetworkPokemonType
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val network: PokemonNetworkDataSource,
    private val pokemonDao: PokemonDao,
    private val abilityDao: AbilityDao,
    private val moveDao: MoveDao,
) {

    fun getMove(id: Int): Flow<NetworkMove> = flow {
        moveDao.findById(id)?.let { move ->
            Timber.i("[sunchulbaek] Move(id = $id) DB에 저장되어 있음")
            emit(NetworkMove(
                id = move.id,
                names = listOf(
                    NetworkName(
                        name = move.name ?: "",
                        language = NetworkNamedAPIResource(
                            name = move.nameLang ?: "",
                        )
                    )
                ),
                flavorTextEntries = listOf(
                    NetworkAbilityFlavorText(
                        flavorText = move.flavor ?: "",
                        language = NetworkNamedAPIResource(
                            name = move.flavorLang ?: "",
                        ),
                    )
                )
            ))
        } ?: run {
            Timber.e("[sunchulbaek] Move(id = $id) DB에 저장되어 있지 않음. API 콜")
            network.getMove(id).let { move ->
                moveDao.insert(Move(
                    id = move.id,
                    name = move.getName(),
                    nameLang = move.getNameLang(),
                    flavor = move.getFlavor(),
                    flavorLang = move.getFlavorLang(),
                ))
                emit(move)
            }
        }
    }

    fun getAbility(id: Int): Flow<NetworkAbility> = flow {
        abilityDao.findById(id)?.let { ability ->
            Timber.i("[sunchulbaek] Ability(id = $id) DB에 저장되어 있음")
            emit(NetworkAbility(
                id = id,
                names = listOf(
                    NetworkName(
                        name = ability.name ?: "",
                        language = NetworkNamedAPIResource(
                            name = ability.nameLang ?: ""
                        ),
                    )
                ),
                flavorTextEntries = listOf(
                    NetworkAbilityFlavorText(
                        flavorText = ability.flavor ?: "",
                        language = NetworkNamedAPIResource(
                            name = ability.flavorLang ?: "",
                        ),
                    )
                )
            ))
        } ?: run {
            Timber.e("[sunchulbaek] Ability(id = $id) DB에 저장되어 있지 않음. API 콜")
            network.getAbility(id).let { ability ->
                abilityDao.insert(
                    Ability(
                        id = ability.id,
                        name = ability.getNameX(),
                        nameLang = ability.getNameLang(),
                        flavor = ability.getFlavor(),
                        flavorLang = ability.getFlavorLang(),
                    )
                )
                emit(ability)
            }
        }
    }

    fun getPokemonList(
        limit: Int? = null,
        offset: Int? = null,
    ): Flow<List<UiPokemon>> = flow {
        emit(network.getPokemonList(
            limit = limit,
            offset = offset
        ).results.map { it.asExternalPokemonModel()!! })
    }

    fun getPokemonDetail(id: Int): Flow<UiPokemonDetail> = flow {
        Timber.i("[sunchulbaek] Pokemon(id = $id) DB에 저장되어 있냐?")
        pokemonDao.findById(id)?.let { pokemon ->
            Timber.i("[sunchulbaek] Pokemon(id = $id) DB에 저장되어 있음")
            emit(NetworkPokemon(
                id = pokemon.id,
                name = pokemon.name ?: "",
                baseExp = pokemon.baseExp,
                height = pokemon.height,
                isDefault = pokemon.isDefault,
                order = pokemon.order,
                weight = pokemon.weight,
                abilities = pokemon.abilityIds.map { abilityId ->
                    NetworkPokemonAbility(
                        isHidden = false,
                        slot = 0,
                        ability = NetworkNamedAPIResource(
                            name = "",
                            url = "https://pokeapi.co/api/v2/ability/$abilityId/"
                        )
                    )
                },
                moves = pokemon.moveIds.map { moveId ->
                    NetworkPokemonMove(
                        move = NetworkNamedAPIResource(
                            name = "",
                            url = "https://pokeapi.co/api/v2/move/$moveId/"
                        )
                    )
                },
                stats = pokemon.stats.map { (name, value) ->
                    NetworkPokemonStat(
                        baseStat = value,
                        stat = NetworkNamedAPIResource(
                            name = name
                        )
                    )
                },
                types = pokemon.types.map { type ->
                    NetworkPokemonType(
                        slot = 0,
                        type = NetworkNamedAPIResource(
                            name = type,
                        )
                    )
                }
            ).asExternalPokemonModel())
        } ?: run {
            Timber.e("[sunchulbaek] Pokemon(id = $id) DB에 저장되어 있지 않음. API 콜")
            network.getPokemonDetail(id).let { pokemon ->
                pokemonDao.insert(
                    Pokemon(
                        id = pokemon.id,
                        name = pokemon.name,
                        baseExp = pokemon.baseExp,
                        height = pokemon.height,
                        isDefault = pokemon.isDefault,
                        order = pokemon.order,
                        weight = pokemon.weight,
                        abilityIds = pokemon.abilities.map {
                            it.ability.url!!.split("/")[6].toInt()
                        },
                        moveIds = pokemon.moves.map {
                            it.move.url!!.split("/")[6].toInt()
                        },
                        stats = pokemon.stats.map {
                            Pair(it.stat.name!!, it.baseStat)
                        },
                        types = pokemon.types.map {
                            it.type.name!!
                        }
                    )
                )
                emit(pokemon.asExternalPokemonModel())
            }
        }
    }
}