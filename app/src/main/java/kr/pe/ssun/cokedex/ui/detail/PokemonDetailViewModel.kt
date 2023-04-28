package kr.pe.ssun.cokedex.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.cokedex.domain.GetPokemonDetailUseCase
import kr.pe.ssun.cokedex.navigation.PokemonDetailArgs
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPokemonDetailUseCase: GetPokemonDetailUseCase,
) : ViewModel() {

    private val args = PokemonDetailArgs(savedStateHandle)

    var uiState = getPokemonDetailUseCase(args.id)
        .map { result ->
            result.getOrNull()?.let { pokemon ->
                PokemonUiState.Success(pokemon)
            } ?: PokemonUiState.Error
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokemonUiState.Loading
        )
}