package kr.pe.ssun.cokedex.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.model.PokemonStat
import kr.pe.ssun.cokedex.model.Type

data class FullPokemon(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "p_id",
        entityColumn = "a_id",
        associateBy = Junction(PokemonAbilityCrossRef::class)
    )
    val abilities: List<AbilityEntity>,
    @Relation(
        parentColumn = "p_id",
        entityColumn = "m_id",
        associateBy = Junction(PokemonMoveCrossRef::class)
    )
    val moves: List<MoveEntity>,
)

fun FullPokemon.asExternalModel() = PokemonDetail(
    id = pokemon.id,
    name = pokemon.name ?: "",
    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
    types = pokemon.types.map { type ->
        Type.fromValue(type)
    },
    abilities = abilities.map { ability -> ability.asExternalModel() },
    totalAbilityIds = pokemon.abilityIds,
    moves = moves.map { move -> move.asExternalModel() },
    totalMoveIds = pokemon.moveIds,
    weight = pokemon.weight,
    height = pokemon.height,
    stats = pokemon.stats.map { (name, value) ->
        PokemonStat(name = name, value = value)
    }
)