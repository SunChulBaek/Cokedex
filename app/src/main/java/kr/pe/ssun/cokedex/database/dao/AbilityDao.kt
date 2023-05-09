package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.AbilityEntity

@Dao
interface AbilityDao {
    @Query("SELECT * FROM ability WHERE id = :abilityId")
    fun findById(abilityId: Int): AbilityEntity?

    @Query("SELECT * FROM ability WHERE id IN (:abilityIds)")
    fun findById(abilityIds: IntArray): List<AbilityEntity>

    @Insert
    fun insert(move: AbilityEntity)
}