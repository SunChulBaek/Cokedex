package kr.pe.ssun.cokedex.ui.detail

import kr.pe.ssun.cokedex.data.model.PokemonDetail

sealed interface PokemonUiState {
    data class Success(
        val pokemon: PokemonDetail
    ) : PokemonUiState
    object Loading : PokemonUiState
    object Error : PokemonUiState
}