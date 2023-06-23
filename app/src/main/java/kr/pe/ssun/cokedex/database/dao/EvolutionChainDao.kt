package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.EvolutionChainEntity

@Dao
interface EvolutionChainDao {
    @Query("SELECT * FROM evolution_chain WHERE c_id = :ecId")
    suspend fun findById(ecId: Int): List<EvolutionChainEntity>

    @Insert
    suspend fun insert(chain: EvolutionChainEntity)
}