package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.EvolutionChainEntity
import timber.log.Timber

data class NetworkEvolutionChain(
    @SerializedName("id") val id: Int,
    @SerializedName("baby_trigger_item") val babyTriggerItem: NetworkNamedAPIResource,
    @SerializedName("chain") val chain: NetworkChainLink,
)

fun NetworkEvolutionChain.asEntity(): List<EvolutionChainEntity> {
    val map = mutableMapOf<Int, Triple<Int?, String, Boolean>>()
    val queue = ArrayDeque<NetworkChainLink>().apply { add(chain) }
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        if (!map.contains(node.species.getId())) {
            map[node.species.getId()] = Triple(
                null,
                node.evolutionDetails.firstOrNull()?.item?.name ?: "0", // TODO
                false
            )
        }
        if (node.evolvesTo.isEmpty()) {
            map[node.species.getId()]?.copy(third = true)?.let { leaf ->
                map[node.species.getId()] = leaf
            }
        }
        node.evolvesTo.forEach { evolveTo ->
            queue.add(evolveTo)
            map[evolveTo.species.getId()] = Triple(
                node.species.getId(),
                evolveTo.evolutionDetails.firstOrNull()?.item?.name ?: "0", // TODO
                false
            )
        }
    }
    return map.map { (key, value) ->
        val (prevId, sName, isLeaf) = value
        EvolutionChainEntity(
            cId = this.id,
            pId = key,
            trigger = sName,
            prevId = prevId,
            isLeaf = isLeaf,
        )
    }
}