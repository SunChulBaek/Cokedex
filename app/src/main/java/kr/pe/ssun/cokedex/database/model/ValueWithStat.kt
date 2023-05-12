package kr.pe.ssun.cokedex.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ValueWithStat(
    @Embedded val value: ValueEntity,
    @Relation(
        parentColumn = "s_id",
        entityColumn = "s_id"
    )
    val stat: StatEntity?
)