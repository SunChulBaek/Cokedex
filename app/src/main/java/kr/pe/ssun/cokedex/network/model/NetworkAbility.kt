package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.data.model.UiAbility

data class NetworkAbility(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("is_main_series") val isMainSeries: Boolean,
    @SerializedName("generation") val generation: NetworkNamedAPIResource,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("effect_entries") val effectEntries: List<NetworkVerboseEffect>,
    @SerializedName("effect_changes") val effectChanges: List<NetworkAbilityEffectChange>,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<NetworkAbilityFlavorText>,
    @SerializedName("pokemon") val pokemon: List<NetworkAbilityPokemon>,
) {
    fun asExternalModel() = UiAbility(
        id = id,
        name = this.getNameX(),
        flavor = this.getFlavorText()
    )

    private fun getNameX(): String? = names.firstOrNull {
        it.language.name == "ko"
    }?.name ?: names.firstOrNull {
        it.language.name == "en"
    }?.name

    private fun getFlavorText(): String? = flavorTextEntries.firstOrNull {
        it.language.name == "ko"
    }?.flavorText ?: flavorTextEntries.firstOrNull {
        it.language.name == "en"
    }?.flavorText
}