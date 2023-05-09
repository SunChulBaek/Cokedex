package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.Ability

@Dao
interface AbilityDao {
    @Query("SELECT * FROM ability WHERE id = :abilityId")
    fun findById(abilityId: Int): Ability?

    @Query("SELECT * FROM ability WHERE id IN (:abilityIds)")
    fun findById(abilityIds: IntArray): List<Ability>

    @Insert
    fun insert(move: Ability)
}