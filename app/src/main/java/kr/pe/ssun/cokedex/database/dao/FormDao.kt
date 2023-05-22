package kr.pe.ssun.cokedex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.pe.ssun.cokedex.database.model.FormEntity

@Dao
interface FormDao {
    @Query("SELECT * FROM form WHERE f_id = :formId")
    fun findById(formId: Int?): FormEntity?

    @Insert
    fun insert(name: FormEntity)
}