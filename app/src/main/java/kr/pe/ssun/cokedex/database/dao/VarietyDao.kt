package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.VarietyEntity

@Dao
interface VarietyDao {
    @Query("SELECT * FROM variety WHERE s_id = :speciesId")
    fun findBySpeciesId(speciesId: Int): List<VarietyEntity>

    @Query("SELECT * FROM variety WHERE v_id IN (:varietyIds)")
    fun findById(varietyIds: IntArray): List<VarietyEntity>

    @Insert
    fun insert(type: VarietyEntity)
}