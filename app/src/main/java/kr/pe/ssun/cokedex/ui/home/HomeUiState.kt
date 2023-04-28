package kr.pe.ssun.cokedex.ui.home

import kr.pe.ssun.cokedex.data.model.Pokemon

sealed interface HomeUiState {
    data class Success(
        val pokemonList: List<Pokemon>,
        val offset: Int,
    ) : HomeUiState
    object Loading : HomeUiState
    object Error : HomeUiState
}