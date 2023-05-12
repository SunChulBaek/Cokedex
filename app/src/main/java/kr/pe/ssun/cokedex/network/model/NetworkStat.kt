package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.StatEntity
import java.util.Locale

data class NetworkStat(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("names") val names: List<NetworkName>
)

fun NetworkStat.asEntity() = StatEntity(
    id = id,
    name = names.firstOrNull {
        it.language.name == Locale.getDefault().language
    }?.name ?: names.firstOrNull {
        it.language.name == "en"
    }?.name
)