package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.data.model.UiAbility

data class NetworkMove(
    @SerializedName("id") val id: Int,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<NetworkAbilityFlavorText>,
) {
    fun asExternalModel() = UiAbility(
        id = id,
        name = getNameX(),
        flavor = getFlavor()
    )

    private fun getNameX(): String? = names.firstOrNull {
        it.language.name == "ko"
    }?.name ?: names.firstOrNull {
        it.language.name == "en"
    }?.name


    private fun getFlavor(): String? = (flavorTextEntries.firstOrNull {
        it.language.name == "ko"
    }?.flavorText ?: flavorTextEntries.firstOrNull {
        it.language.name == "en"
    }?.flavorText)?.replace("\n", " ")
}

