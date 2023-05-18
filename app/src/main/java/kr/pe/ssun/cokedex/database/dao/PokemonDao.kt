package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import kr.pe.ssun.cokedex.database.model.FullPokemon
import kr.pe.ssun.cokedex.database.model.PokemonAbilityCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonEntity
import kr.pe.ssun.cokedex.database.model.PokemonStatCrossRef
import kr.pe.ssun.cokedex.database.model.PokemonTypeCrossRef

@Dao
interface PokemonDao {
    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM pokemon WHERE p_id = :pokemonId")
    fun findById(pokemonId: Int): FullPokemon?

    @Insert
    fun insert(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(p2t: PokemonTypeCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(p2s: PokemonStatCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(p2a: PokemonAbilityCrossRef)
}