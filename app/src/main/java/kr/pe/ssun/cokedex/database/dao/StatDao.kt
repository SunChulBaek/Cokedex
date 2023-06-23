package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.StatEntity

@Dao
interface StatDao {
    @Query("SELECT * FROM stat WHERE s_id = :statId")
    suspend fun findById(statId: Int): StatEntity?

    @Insert
    suspend fun insert(stat: StatEntity)
}