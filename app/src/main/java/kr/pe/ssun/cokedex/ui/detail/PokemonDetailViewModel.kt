package kr.pe.ssun.cokedex.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.cokedex.domain.GetEvolutionChainUseCase
import kr.pe.ssun.cokedex.domain.GetPokemonDetailUseCase
import kr.pe.ssun.cokedex.domain.GetTypeUseCase
import kr.pe.ssun.cokedex.model.Type
import kr.pe.ssun.cokedex.navigation.PokemonDetailArgs
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPokemonDetailUseCase: GetPokemonDetailUseCase,
    getTypeUseCase: GetTypeUseCase,
    getEvolutionChainUseCase: GetEvolutionChainUseCase,
) : ViewModel() {

    companion object {
        const val DUMMY_ID = 0
    }

    private val args = PokemonDetailArgs(savedStateHandle)

    private val typeIds = MutableStateFlow(listOf(DUMMY_ID))

    private val typeFlow = typeIds.flatMapConcat { it.asFlow() }
        .map { typeId ->
            getTypeUseCase(typeId).first().getOrNull() ?: Type(DUMMY_ID)
        }

    private var types: List<Type> = mutableListOf()

    private var evolutionChains: List<List<Pair<Int, String>>> = mutableListOf()

    var uiState = combine(
        typeFlow,
        getPokemonDetailUseCase(args.id)
    ) { type, result ->
        val pokemon = result.getOrNull()?.copy()

        // 더미 아이템 하나만 있을 때 캐시 안된 Type api 호출하도록
        if (typeIds.value.size == 1) {
            types = pokemon?.types?.map { it.copy(fromDB = true) } ?: listOf()
            val notCachedTIds = pokemon?.totalTypeIds?.filterNot { tId ->
                pokemon.types.map { it.id }.contains(tId)
            } ?: listOf()
            typeIds.emit(notCachedTIds)
        }

        when {
            pokemon != null -> {
                // Type 중복되지 않게 아이템 추가
                if (type.id != DUMMY_ID && types.none { it.id == type.id }) {
                    types = types.filterNot { it.id == DUMMY_ID }.plus(type).sortedBy { it.id }
                }

                pokemon.evolutionChainId?.let { ecId ->
                    getEvolutionChainUseCase(ecId).first().getOrNull()?.let { chains ->
                        evolutionChains = chains.chains
                    }
                }

                PokemonUiState.Success(
                    pokemon = pokemon.copy(
                        types = types,
                        evolutionChains = evolutionChains,
                    ),
                    colorStart = args.colorStart,
                    colorEnd = args.colorEnd
                )
            }
            else -> PokemonUiState.Error
        }
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PokemonUiState.Loading(
            id = args.id,
            name = args.name,
            imageUrl = args.imageUrl,
            colorStart = args.colorStart,
            colorEnd = args.colorEnd,
        )
    )
}