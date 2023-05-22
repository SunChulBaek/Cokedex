package kr.pe.ssun.cokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kr.pe.ssun.cokedex.model.EvolutionChain
import kr.pe.ssun.cokedex.model.Form
import kr.pe.ssun.cokedex.model.Loadable
import kr.pe.ssun.cokedex.model.PokemonDetail
import kr.pe.ssun.cokedex.model.Species
import kr.pe.ssun.cokedex.model.findById
import kr.pe.ssun.cokedex.util.asSp
import timber.log.Timber

@Composable
fun PokemonDetailLoadingProgress(
    modifier: Modifier = Modifier,
    pokemon: PokemonDetail?
) = Column(
    modifier = modifier.background(Color(0xFFe0e0e0)),
) {
    // Form, Type, Species, Evolution Chain 로딩 상태를 표시함
    Timber.d("[sunchulbaek][Progress] ec ${pokemon?.evolutionChain?.id}/${pokemon?.evolutionChainId}")
    listOf(
        // Form
        Triple("Form", mutableListOf<Form>().apply {
            pokemon?.form?.let { form -> this.add(form) }
        }, listOf(pokemon?.formId ?: 0)),
        // Type
        Triple("Type", pokemon?.types, pokemon?.totalTypeIds),
        // Species
        Triple("Species", mutableListOf<Species>().apply {
            pokemon?.species?.let { species -> this.add(species) }
        }, listOf(pokemon?.speciesId ?: 0)),
        // Evolution Chain
        Triple("Evolution", mutableListOf<EvolutionChain>().apply {
            pokemon?.evolutionChain?.let { ec -> this.add(ec) }
        }, listOf(pokemon?.evolutionChainId ?: 0))
    ).forEach { (label, items, ids) ->
        LoadingProgress(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = label,
            items = items,
            totalId = ids,
        )
    }
}

@Composable
private fun LoadingProgress(
    modifier: Modifier = Modifier,
    label: String = "",
    items: List<Loadable>?,
    totalId: List<Int>?,
    colorFromInternet: Color = Color(0xFF4caf50),
    colorFromDB: Color = Color(0xFF3f51b5),
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        modifier = Modifier.width(20.dp).background(Color.White).border(0.3.dp, Color.Black),
        text = label,
        style = TextStyle(fontSize = 5.dp.asSp(), color = Color.Black)
    )
    totalId?.forEach { id ->
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    when (items?.findById(id)?.fromDB) {
                        true -> colorFromDB
                        false -> colorFromInternet
                        else -> Color.Transparent
                    }
                )
                .border(0.3.dp, Color.Black)
        )
    }
}