package kr.pe.ssun.cokedex.ui.detail

sealed interface PokemonUiState {
    data class Success(
        val id: Int,
        val name: String,
        val imageUrl: String,
    ) : PokemonUiState
    object Loading : PokemonUiState
    object Error : PokemonUiState
}