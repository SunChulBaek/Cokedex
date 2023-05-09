package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.Move

@Dao
interface MoveDao {
    @Query("SELECT * FROM move WHERE id = :moveId")
    fun findById(moveId: Int): Move?

    @Query("SELECT * FROM move WHERE id IN (:moveIds)")
    fun findById(moveIds: IntArray): List<Move>

    @Insert
    fun insert(move: Move)
}