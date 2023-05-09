package kr.pe.ssun.cokedex.ui.detail

import kr.pe.ssun.cokedex.model.PokemonDetail

sealed interface PokemonUiState {
    data class Success(
        val pokemon: PokemonDetail,
        val colorStart: Long,
        val colorEnd: Long,
    ) : PokemonUiState
    data class Loading(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val colorStart: Long,
        val colorEnd: Long,
    ) : PokemonUiState
    object Error : PokemonUiState
}