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
import kr.pe.ssun.cokedex.domain.GetMovesUseCase
import kr.pe.ssun.cokedex.domain.GetPokemonDetailUseCase
import kr.pe.ssun.cokedex.navigation.PokemonDetailArgs
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPokemonDetailUseCase: GetPokemonDetailUseCase,
    getAbilityUseCase: GetAbilityUseCase,
    getMoveUseCase: GetMovesUseCase
) : ViewModel() {

    companion object {
        const val DUMMY_ID = 0
    }

    private val args = PokemonDetailArgs(savedStateHandle)

    private val abilityIds = MutableStateFlow(listOf(DUMMY_ID))

    private val moveIds = MutableStateFlow(listOf(DUMMY_ID))

    // TODO
    private val abilitiesFlow = abilityIds.flatMapConcat { it.asFlow() }
        .map { abilityId ->
            getAbilityUseCase(abilityId).first().getOrNull() ?: Ability(DUMMY_ID)
        }

    // TODO
    private val movesFlow = moveIds.flatMapConcat { it.asFlow() }
        .map { moveIds ->
            getMoveUseCase(moveIds).first().getOrNull() ?: Ability(DUMMY_ID)
        }

    private var abilities: List<Ability> = mutableListOf()

    private var moves: List<Ability> = mutableListOf()

    var uiState = combine(
        abilitiesFlow,
        movesFlow,
        getPokemonDetailUseCase(args.id)
    ) { ability, move, result ->
        val pokemon = result.getOrNull()?.copy()

        // 더미 아이템 하나만 있을 때 Ability api 호출하도록
        if (abilityIds.value.size == 1) {
            abilityIds.emit(pokemon?.abilities?.map { it.id } ?: listOf())
        }
        // 더미 아이템 하나만 있을 때 Move api 호출하도록
        if (moveIds.value.size == 1) {
            moveIds.emit(pokemon?.moves?.map { it.id } ?: listOf())
        }

        when {
            pokemon != null -> {
                // Ability, Move 중복되지 않게 아이템 추가
                if (abilities.none { it.id == ability.id }) {
                    abilities = abilities.filterNot { it.id == DUMMY_ID }.plus(ability)
                }
                if (moves.none { it.id == move.id }) {
                    moves = moves.filterNot { it.id == DUMMY_ID }.plus(move)
                }

                PokemonUiState.Success(
                    pokemon = pokemon.copy(
                        abilities = abilities,
                        moves = moves,
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