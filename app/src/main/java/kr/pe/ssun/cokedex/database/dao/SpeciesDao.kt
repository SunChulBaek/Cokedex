package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.SpeciesEntity

@Dao
interface SpeciesDao {
    @Query("SELECT * FROM name WHERE p_id = :pokemonId")
    fun findById(pokemonId: Int): SpeciesEntity?

    @Insert
    fun insert(name: SpeciesEntity)
}