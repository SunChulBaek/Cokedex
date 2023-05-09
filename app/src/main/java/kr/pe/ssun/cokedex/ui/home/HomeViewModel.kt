package kr.pe.ssun.cokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.domain.GetPokemonListParam
import kr.pe.ssun.cokedex.domain.GetPokemonListUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    val param = MutableStateFlow<GetPokemonListParam?>(null)

    private val list = mutableListOf<Pokemon>()

    val uiState = param.flatMapConcat { param ->
        getPokemonListUseCase(param)
    }.map { result ->
        result.getOrNull()?.let { pokemonList ->
            list.addAll(pokemonList)
            HomeUiState.Success(list, list.size)
        } ?: HomeUiState.Error
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )
}