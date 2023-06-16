package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "flavor_text",
    primaryKeys = [ "s_id", "language", "version" ],
)
data class FlavorTextEntity(
    @ColumnInfo(name = "s_id") val sId: Int,
    @ColumnInfo(name = "language") val lang: String,
    @ColumnInfo(name = "flavor_text") val flavorText: String,
    @ColumnInfo(name = "version") val version: String,
)