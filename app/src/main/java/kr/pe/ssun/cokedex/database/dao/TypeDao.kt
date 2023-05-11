package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.TypeEntity

@Dao
interface TypeDao {
    @Query("SELECT * FROM type WHERE t_id = :typeId")
    fun findById(typeId: Int): TypeEntity?

    @Query("SELECT * FROM type WHERE t_id IN (:typeIds)")
    fun findById(typeIds: IntArray): List<TypeEntity>

    @Insert
    fun insert(type: TypeEntity)
}