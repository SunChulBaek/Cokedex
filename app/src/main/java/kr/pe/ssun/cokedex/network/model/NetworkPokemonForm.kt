package kr.pe.ssun.cokedex.network.model

import com.google.gson.annotations.SerializedName
import kr.pe.ssun.cokedex.database.model.FormEntity
import kr.pe.ssun.cokedex.database.model.LangValue

data class NetworkPokemonForm(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int,
    @SerializedName("form_order") val formOrder: Int,
    @SerializedName("is_default") val isDefault: Boolean,
    @SerializedName("is_battle_only") val isBattleOnly: Boolean,
    @SerializedName("is_mega") val isMega: Boolean,
    @SerializedName("form_name") val formName: String,
    @SerializedName("names") val names: List<NetworkName>,
    @SerializedName("form_names") val formNames: List<NetworkName>,
)

fun NetworkPokemonForm.asEntity() = FormEntity(
    id = id,
    names = formNames.map { name ->
        LangValue(name.language.name!!, name.name)
    }
)