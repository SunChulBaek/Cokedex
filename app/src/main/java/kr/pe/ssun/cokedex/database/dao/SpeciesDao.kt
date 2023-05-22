package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.SpeciesEntity

@Dao
interface SpeciesDao {
    @Query("SELECT * FROM name WHERE s_id = :speciesId")
    fun findById(speciesId: Int): SpeciesEntity?

    @Insert
    fun insert(name: SpeciesEntity)
}