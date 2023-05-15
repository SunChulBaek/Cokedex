package kr.pe.ssun.cokedex.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.model.Stat
import kr.pe.ssun.cokedex.model.Type

data class FullPokemon(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "p_id",
        entityColumn = "p_id"
    )
    val name: NameEntity?,
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
    @Relation(
        parentColumn = "p_id",
        entityColumn = "p_id",
        entity = ValueEntity::class
    )
    val stats: List<ValueWithStat>,
    @Relation(
        parentColumn = "p_id",
        entityColumn = "t_id",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<TypeEntity>
)

fun FullPokemon.asExternalModel() = PokemonDetail(
    id = pokemon.id,
    name = name?.name ?: (pokemon.name ?: ""),
    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
    totalTypeIds = pokemon.typeIds,
    types = types.map { type -> Type(type.id, type.name) },
    abilities = abilities.map { ability -> ability.asExternalModel() },
    totalAbilityIds = pokemon.abilityIds,
    moves = moves.map { move -> move.asExternalModel() },
    totalMoveIds = pokemon.moveIds,
    weight = pokemon.weight,
    height = pokemon.height,
    stats = stats.map { stat ->
        Stat(stat.value.sId, stat.stat?.name, stat.value.value)
    },
)