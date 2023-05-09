package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ability(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "nameLang") val nameLang: String?,
    @ColumnInfo(name = "flavor") val flavor: String?,
    @ColumnInfo(name = "flavorLang") val flavorLang: String?,
)