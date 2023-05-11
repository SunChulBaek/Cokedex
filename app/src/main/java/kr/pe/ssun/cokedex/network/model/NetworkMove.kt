package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.model.Ability

data class NetworkMove(
    @SerializedName("id") val id: Int,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<NetworkAbilityFlavorText>,
) {
    fun asExternalModel() = Ability(
        id = id,
        name = getName(),
        flavor = getFlavor()
    )

    fun getName(): String? = names.firstOrNull {
        it.language.name == "ko"
    }?.name ?: names.firstOrNull {
        it.language.name == "en"
    }?.name

    fun getNameLang(): String? = names.firstOrNull {
        it.language.name == "ko"
    }?.language?.name ?: names.firstOrNull {
        it.language.name == "en"
    }?.language?.name


    fun getFlavor(): String? = (flavorTextEntries.firstOrNull {
        it.language.name == "ko"
    }?.flavorText ?: flavorTextEntries.firstOrNull {
        it.language.name == "en"
    }?.flavorText)?.replace("\n", " ")

    fun getFlavorLang(): String? = flavorTextEntries.firstOrNull {
        it.language.name == "ko"
    }?.language?.name ?: flavorTextEntries.firstOrNull {
        it.language.name == "en"
    }?.language?.name
}
