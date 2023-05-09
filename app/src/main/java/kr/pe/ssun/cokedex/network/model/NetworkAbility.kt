package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.model.Ability

data class NetworkAbility(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("is_main_series") val isMainSeries: Boolean = false,
    @SerializedName("generation") val generation: NetworkNamedAPIResource? = null,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("effect_entries") val effectEntries: List<NetworkVerboseEffect> = listOf(),
    @SerializedName("effect_changes") val effectChanges: List<NetworkAbilityEffectChange> = listOf(),
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<NetworkAbilityFlavorText> = listOf(),
    @SerializedName("pokemon") val pokemon: List<NetworkAbilityPokemon> = listOf(),
) {
    fun asExternalModel() = Ability(
        id = id,
        name = this.getNameX(),
        flavor = this.getFlavor()
    )

    fun getNameX(): String? = names.firstOrNull {
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