package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.PokemonItemEntity

@Dao
interface PokemonItemDao {
    @Query("SELECT * FROM pokemon_item WHERE indexx in (:indexx)")
    fun findByIndex(indexx: IntArray): List<PokemonItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(items: List<PokemonItemEntity>)
}