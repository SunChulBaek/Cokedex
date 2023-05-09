package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon WHERE id = :pokemonId")
    fun findById(pokemonId: Int): PokemonEntity?

    @Insert
    fun insert(pokemon: PokemonEntity)
}