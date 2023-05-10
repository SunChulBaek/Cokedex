package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.MoveEntity

@Dao
interface MoveDao {
    @Query("SELECT * FROM move WHERE m_id = :moveId")
    fun findById(moveId: Int): MoveEntity?

    @Query("SELECT * FROM move WHERE m_id IN (:moveIds)")
    fun findById(moveIds: IntArray): List<MoveEntity>

    @Insert
    fun insert(move: MoveEntity)
}