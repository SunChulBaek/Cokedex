package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.EvolutionChainEntity

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
            map[node.species.getId()] = Triple(null, node.species.name!!, false)
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
                evolveTo.species.name!!,
                false
            )
        }
    }
    return map.map { node ->
        EvolutionChainEntity(
            cId = this.id,
            pId = node.key,
            name = node.value.second,
            prevId = node.value.first,
            isLeaf = node.value.third,
        )
    }
}