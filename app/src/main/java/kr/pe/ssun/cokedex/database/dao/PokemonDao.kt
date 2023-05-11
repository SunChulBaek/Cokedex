package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import kr.pe.ssun.cokedex.database.model.FullPokemon
import kr.pe.ssun.cokedex.database.model.PokemonAbilityCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonMoveCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.database.model.PokemonTypeCrossRef
import kr.pe.ssun.cokedex.database.model.StatEntity

@Dao
interface PokemonDao {
    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM pokemon WHERE p_id = :pokemonId")
    fun findById(pokemonId: Int): FullPokemon?

    @Insert
    fun insert(pokemon: PokemonEntity)

    @Insert
    fun insert(stat: StatEntity)

    @Insert
    fun insert(p2t: PokemonTypeCrossRef)

    @Insert
    fun insert(p2a: PokemonAbilityCrossRef)

    @Insert
    fun insert(p2m: PokemonMoveCrossRef)
}