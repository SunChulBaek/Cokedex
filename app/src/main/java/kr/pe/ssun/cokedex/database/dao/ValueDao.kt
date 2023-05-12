package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.ValueEntity
import kr.pe.ssun.cokedex.database.model.ValueWithStat

@Dao
interface ValueDao {
    @Query("SELECT * FROM value WHERE p_id = :pokemonId AND s_id = :statId")
    fun findById(pokemonId: Int, statId: Int): ValueEntity?

    @Query("SELECT * FROM value WHERE p_id = :pokemonId AND s_id IN (:statIds)")
    fun findById(pokemonId: Int, statIds: IntArray): List<ValueWithStat>

    @Insert
    fun insert(value: ValueEntity)
}