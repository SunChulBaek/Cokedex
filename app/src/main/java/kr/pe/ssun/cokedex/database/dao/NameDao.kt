package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.NameEntity

@Dao
interface NameDao {
    @Query("SELECT * FROM name WHERE p_id = :pokemonId")
    fun findById(pokemonId: Int): NameEntity?

    @Insert
    fun insert(name: NameEntity)
}