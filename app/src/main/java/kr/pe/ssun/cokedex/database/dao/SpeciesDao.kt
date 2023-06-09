package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.SpeciesEntity

@Dao
interface SpeciesDao {
    @Query("SELECT * FROM species WHERE s_id = :speciesId")
    suspend fun findById(speciesId: Int): SpeciesEntity?

    @Query("SELECT * FROM species")
    suspend fun selectAll(): List<SpeciesEntity>

    @Insert
    suspend fun insert(species: SpeciesEntity)
}