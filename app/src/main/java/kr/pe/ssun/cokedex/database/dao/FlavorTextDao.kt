package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import kr.pe.ssun.cokedex.database.model.FlavorTextEntity

@Dao
interface FlavorTextDao {
    @Insert
    fun insert(flavorTexts: List<FlavorTextEntity>)
}