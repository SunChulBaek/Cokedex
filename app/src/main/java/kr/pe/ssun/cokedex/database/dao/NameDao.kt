package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import kr.pe.ssun.cokedex.database.model.NameEntity

@Dao
interface NameDao {
    @Insert
    fun insert(names: List<NameEntity>)
}