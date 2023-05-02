package kr.pe.ssun.cokedex.ui.detail

import kr.pe.ssun.cokedex.data.model.UiPokemonDetail

sealed interface PokemonUiState {
    data class Success(
        val pokemon: UiPokemonDetail,
        val colorStart: Long,
        val colorEnd: Long,
    ) : PokemonUiState
    object Loading : PokemonUiState
    object Error : PokemonUiState
}