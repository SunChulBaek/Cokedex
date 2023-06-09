package kr.pe.ssun.cokedex.ui.detail

import kr.pe.ssun.cokedex.model.PokemonDetail

sealed interface PokemonUiState {
    data class Success(
        val pokemon: PokemonDetail, // 프로그레스 표시용
        val items: List<PokemonDetailItem>,
        val colorStart: Int,
        val colorEnd: Int,
    ) : PokemonUiState
    data class Loading(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val colorStart: Int,
        val colorEnd: Int,
    ) : PokemonUiState
    object Error : PokemonUiState
}