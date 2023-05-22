package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import java.util.Locale

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
)