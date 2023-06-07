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
import kr.pe.ssun.cokedex.domain.GetFormUseCase
import kr.pe.ssun.cokedex.domain.GetSpeciesUseCase
import kr.pe.ssun.cokedex.domain.GetPokemonDetailUseCase
import kr.pe.ssun.cokedex.domain.GetTypeUseCase
import kr.pe.ssun.cokedex.model.EvolutionChain
import kr.pe.ssun.cokedex.model.Form
import kr.pe.ssun.cokedex.model.Species
import kr.pe.ssun.cokedex.model.Type
import kr.pe.ssun.cokedex.navigation.PokemonDetailArgs
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPokemonDetailUseCase: GetPokemonDetailUseCase,
    getTypeUseCase: GetTypeUseCase,
    getSpeciesUseCase: GetSpeciesUseCase,
    getEvolutionChainUseCase: GetEvolutionChainUseCase,
    getFormUseCase: GetFormUseCase,
) : ViewModel() {

    companion object {
        const val DUMMY_ID = 0
    }

    private val args = PokemonDetailArgs(savedStateHandle)

    private val typeIds = MutableStateFlow(listOf(DUMMY_ID))

    private val speciesId = MutableStateFlow(DUMMY_ID)

    private val evolutionChainId = MutableStateFlow(DUMMY_ID)

    private val formId = MutableStateFlow(DUMMY_ID)

    private val typeFlow = typeIds.flatMapConcat { it.asFlow() }
        .map { typeId ->
            getTypeUseCase(typeId).first().getOrNull() ?: Type(DUMMY_ID)
        }

    private val speciesFlow = speciesId
        .map { speciesId ->
            getSpeciesUseCase(speciesId).first().getOrNull() ?: Species(DUMMY_ID)
        }

    private val evolutionChainFlow = evolutionChainId
        .map { ecId ->
            getEvolutionChainUseCase(ecId).first().getOrNull() ?: EvolutionChain(DUMMY_ID)
        }

    private val formFlow = formId
        .map { formId ->
            getFormUseCase(formId).first().getOrNull() ?: Form(DUMMY_ID)
        }

    private var types: List<Type> = mutableListOf()

    var uiState = combine(
        typeFlow,
        speciesFlow,
        evolutionChainFlow,
        formFlow,
        getPokemonDetailUseCase(args.id)
    ) { type, species, evolutionChains, form, result ->
        val pokemon = result.getOrNull()?.copy()

        // 더미 아이템 하나만 있을 때 캐시 안된 Type api 호출하도록
        if (typeIds.value.size == 1) {
            types = pokemon?.types?.map { it.copy(fromDB = true) } ?: listOf()
            val notCachedTIds = pokemon?.totalTypeIds?.filterNot { tId ->
                pokemon.types.map { it.id }.contains(tId)
            } ?: listOf()
            typeIds.emit(notCachedTIds)
        }

        // Species api 호출
        if (speciesId.value == DUMMY_ID && pokemon?.speciesId != null) {
            speciesId.emit(pokemon.speciesId)
        }

        // Evolution Chain api 호출
        if (evolutionChainId.value == DUMMY_ID
            && (species.ecId != null || pokemon?.evolutionChainId != null)) {
            // species가 캐싱되어 있으면 pokemon.evolutionChainId를 사용
            // 캐싱 되어 있지 않으면 Species를 호출 한 다음 Evolution Chain 호출
            evolutionChainId.emit(species.ecId ?: pokemon?.evolutionChainId ?: DUMMY_ID)
        }

        // Form api 호출
        if (formId.value == DUMMY_ID && pokemon?.formId != null) {
            formId.emit(pokemon.formId)
        }

        when {
            pokemon != null -> {
                // Type 중복되지 않게 아이템 추가
                if (type.id != DUMMY_ID && types.none { it.id == type.id }) {
                    types = types.filterNot { it.id == DUMMY_ID }.plus(type).sortedBy { it.id }
                }
                if (species.id != DUMMY_ID) {
                    Timber.d("[sunchulbaek] sId = ${species.id}, sName = ${species.name}, ecId = ${species.ecId}")
                }
                if (form.id != DUMMY_ID) {
                    Timber.d("[sunchulbaek] fId = ${form.id} fName = ${form.name}")
                }

                PokemonUiState.Success(
                    pokemon = pokemon.copy(
                        name = species.name ?: pokemon.name,
                        species = species,
                        form = form,
                        types = types,
                        evolutionChainId = species.ecId,
                        evolutionChain = evolutionChains,
                        varietyIds = species.vIds ?: listOf()
                    ), // 프로그레스 표시용. 리스트에 보이는 것은 아래 items로 구성
                    items = mutableListOf<PokemonDetailItem>().apply {
                        add(PokemonDetailImage(id = pokemon.id))
                        add(PokemonDetailName(
                            id = pokemon.id,
                            name = "${species.name ?: pokemon.name}${if (form.name != null) " (${form.name})" else ""}")
                        )
                        add(PokemonDetailStat(
                            weight = pokemon.weight.toFloat() / 10,
                            types = pokemon.types,
                            height = pokemon.height.toFloat() / 10
                        ))
                        if (species.flavorText != null) {
                            add(
                                PokemonDetailFlavorText(
                                    flavorText = species.flavorText
                                )
                            )
                        }
                        if (maxEvolutionChainLength(evolutionChain = evolutionChains) > 1) {
                            add(PokemonDetailEvolution(evolutionChain = evolutionChains, hostId = pokemon.id))
                        }
                        if ((species.vIds?.size ?: 0) > 1) {
                            add(PokemonDetailVarieties(varietyIds = species.vIds, hostId = pokemon.id))
                        }
                    },
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