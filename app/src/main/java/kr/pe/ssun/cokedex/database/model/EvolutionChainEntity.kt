package kr.pe.ssun.cokedex.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import kr.pe.ssun.cokedex.model.EvolutionChain

@Entity(
    tableName = "evolution_chain",
    primaryKeys = [ "c_id", "p_id" ]
)
data class EvolutionChainEntity(
    @ColumnInfo(name = "c_id") val cId: Int,
    @ColumnInfo(name = "p_id") val pId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "prev_id") val prevId: Int?,
    @ColumnInfo(name = "is_leaf") val isLeaf: Boolean,
)

fun List<EvolutionChainEntity>.asExternalModel(fromDB: Boolean = false) = EvolutionChain(
    id = firstOrNull()?.cId ?: 0,
    chains = filter { it.isLeaf }.map { leaf ->
        val list = mutableListOf<Pair<Int, String>>()
        var entry: EvolutionChainEntity? = leaf
        while (entry != null) {
            list.add(Pair(entry.pId, entry.name))
            entry = firstOrNull { it.pId == entry?.prevId }
        }
        list.apply { reverse() }
    },
    fromDB = fromDB
)