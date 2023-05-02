package kr.pe.ssun.cokedex.ui.home

import kr.pe.ssun.cokedex.data.model.UiPokemon

sealed interface HomeUiState {
    data class Success(
        val pokemonList: List<UiPokemon>,
        val offset: Int,
    ) : HomeUiState
    object Loading : HomeUiState
    object Error : HomeUiState
}