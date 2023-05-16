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
import kr.pe.ssun.cokedex.model.Ability
import kr.pe.ssun.cokedex.domain.GetAbilityUseCase
import kr.pe.ssun.cokedex.domain.GetEvolutionChainUseCase
import kr.pe.ssun.cokedex.domain.GetMovesUseCase
import kr.pe.ssun.cokedex.domain.GetPokemonDetailUseCase
import kr.pe.ssun.cokedex.domain.GetStatUseCase
import kr.pe.ssun.cokedex.domain.GetTypeUseCase
import kr.pe.ssun.cokedex.model.Stat
import kr.pe.ssun.cokedex.model.Type
import kr.pe.ssun.cokedex.navigation.PokemonDetailArgs
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPokemonDetailUseCase: GetPokemonDetailUseCase,
    getTypeUseCase: GetTypeUseCase,
    getStatUseCase: GetStatUseCase,
    getAbilityUseCase: GetAbilityUseCase,
    // getMoveUseCase: GetMovesUseCase,
    getEvolutionChainUseCase: GetEvolutionChainUseCase,
) : ViewModel() {

    companion object {
        const val DUMMY_ID = 0
    }

    private val args = PokemonDetailArgs(savedStateHandle)

    private val typeIds = MutableStateFlow(listOf(DUMMY_ID))

    private val statIds = MutableStateFlow(listOf(Pair(DUMMY_ID, DUMMY_ID)))

    private val abilityIds = MutableStateFlow(listOf(DUMMY_ID))

    private val moveIds = MutableStateFlow(listOf(DUMMY_ID))

    private val typeFlow = typeIds.flatMapConcat { it.asFlow() }
        .map { typeId ->
            getTypeUseCase(typeId).first().getOrNull() ?: Type(DUMMY_ID)
        }

    private val statFlow = statIds.flatMapConcat { it.asFlow() }
        .map { statId ->
            getStatUseCase(statId).first().getOrNull() ?: Stat(DUMMY_ID)
        }

    private val abilitiesFlow = abilityIds.flatMapConcat { it.asFlow() }
        .map { abilityId ->
            getAbilityUseCase(abilityId).first().getOrNull() ?: Ability(DUMMY_ID)
        }

//    private val movesFlow = moveIds.flatMapConcat { it.asFlow() }
//        .map { moveIds ->
//            getMoveUseCase(moveIds).first().getOrNull() ?: Ability(DUMMY_ID)
//        }

    private var types: List<Type> = mutableListOf()

    private var stats: List<Stat> = mutableListOf()

    private var abilities: List<Ability> = mutableListOf()

//    private var moves: List<Ability> = mutableListOf()

    private var evolutionChains: List<List<Pair<Int, String>>> = mutableListOf()

    var uiState = combine(
        typeFlow,
        statFlow,
        abilitiesFlow,
        // movesFlow,
        getPokemonDetailUseCase(args.id)
    ) { type, stat, ability, result ->
        val pokemon = result.getOrNull()?.copy()

        // 더미 아이템 하나만 있을 때 캐시 안된 Type api 호출하도록
        if (typeIds.value.size == 1) {
            types = pokemon?.types?.map { it.copy(fromDB = true) } ?: listOf()
            val notCachedTIds = pokemon?.totalTypeIds?.filterNot { tId ->
                pokemon.types.map { it.id }.contains(tId)
            } ?: listOf()
            typeIds.emit(notCachedTIds)
        }

        // 더미 아이템 하나만 있을 때 캐시 안된 Stat api 호출하도록
        if (statIds.value.size == 1) {
            stats = pokemon?.stats?.filterNot { it.name == null }?.map { it.copy(fromDB = true) } ?: listOf()
            val notCachedSIds = pokemon?.stats?.filter {
                it.name == null
            }?.map { Pair(it.id, it.value ?: 0) } ?: listOf()
            statIds.emit(notCachedSIds)
        }

        // 더미 아이템 하나만 있을 때 캐시 안된 Ability api 호출하도록
        if (abilityIds.value.size == 1) {
            abilities = pokemon?.abilities?.map { it.copy(fromDB = true) } ?: listOf()
            val notCachedAIds = pokemon?.totalAbilityIds?.filterNot { aId ->
                pokemon.abilities.map { it.id }.contains(aId)
            } ?: listOf()
            abilityIds.emit(notCachedAIds)
        }

        // 더미 아이템 하나만 있을 때 캐시 안된 Move api 호출하도록
//        if (moveIds.value.size == 1) {
//            moves = pokemon?.moves?.map { it.copy(fromDB = true) } ?: listOf()
//            val notCachedMIds = pokemon?.totalMoveIds?.filterNot { mId ->
//                pokemon.moves.map { it.id }.contains(mId)
//            } ?: listOf()
//            moveIds.emit(notCachedMIds)
//        }

        when {
            pokemon != null -> {
                // Type, Stat, Ability, Move 중복되지 않게 아이템 추가
                if (type.id != DUMMY_ID && types.none { it.id == type.id }) {
                    types = types.filterNot { it.id == DUMMY_ID }.plus(type).sortedBy { it.id }
                }
                if (stat.id != DUMMY_ID && stats.none { it.id == stat.id }) {
                    stats = stats.filterNot { it.id == DUMMY_ID }.plus(stat).sortedBy { it.id }
                }
                if (ability.id != DUMMY_ID && abilities.none { it.id == ability.id }) {
                    abilities = abilities.filterNot { it.id == DUMMY_ID }.plus(ability).sortedBy { it.id }
                }
//                if (move.id != DUMMY_ID && moves.none { it.id == move.id }) {
//                    moves = moves.filterNot { it.id == DUMMY_ID }.plus(move).sortedBy { it.id }
//                }
                pokemon.evolutionChainId?.let { ecId ->
                    getEvolutionChainUseCase(ecId).first().getOrNull()?.let { chains ->
                        evolutionChains = chains.chains
                    }
                }

                PokemonUiState.Success(
                    pokemon = pokemon.copy(
                        types = types,
                        stats = stats,
                        abilities = abilities,
//                        moves = moves,
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