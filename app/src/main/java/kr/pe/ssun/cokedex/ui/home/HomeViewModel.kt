package kr.pe.ssun.cokedex.ui.home

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
import kr.pe.ssun.cokedex.domain.GetNameUseCase
import kr.pe.ssun.cokedex.model.Pokemon
import kr.pe.ssun.cokedex.domain.GetPokemonListParam
import kr.pe.ssun.cokedex.domain.GetPokemonListUseCase
import kr.pe.ssun.cokedex.model.Species
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase,
    getNameUseCase: GetNameUseCase,
) : ViewModel() {

    companion object {
        const val DUMMY_ID = 0
        private val SHORTCUTS = listOf(
            10034, // 메가리자몽X
            10196, // 리자몽 거다이맥스
            1008, // 미라이돈 (진화 없음)
            936, // 카디나르마 (1단계, 2분기)
            135, // 쥬피썬더 (1단계, 8분기)
            269, // 독케일 (2단계, 1단계 분기)
            792, // 루나아라 (2단계, 2단계 분기)
        ) // 다양한 진화 형태..
    }

    val param = MutableStateFlow<GetPokemonListParam?>(null)

    private val pokemonListFlow = param.flatMapConcat { param ->
        getPokemonListUseCase(param)
    }

    private val namesIds = MutableStateFlow(listOf(DUMMY_ID).plus(SHORTCUTS))

    private val namesFlow = namesIds.flatMapConcat { it.asFlow() }
        .map { nameId ->
            getNameUseCase(nameId).first().getOrNull() ?: Species(DUMMY_ID)
        }

    private val list = mutableListOf<Pokemon>()
    private val names = HashMap<Int, String>()

    val uiState = combine(
        pokemonListFlow,
        namesFlow
    ) { pokemonListResult, newName ->
        // 이름 정보 업뎃
        if (!names.contains(newName.id) && newName.name != null) {
            names[newName.id] = newName.name
        }

        // 리스트 통합
        pokemonListResult.getOrNull()?.let { pokemonList ->
            list.addAll(pokemonList.filterNot { list.contains(it) })
        }

        // 이름 정보 요청
        namesIds.emit(list.filter { pokemon -> !names.contains(pokemon.id) }.map { it.id })

        HomeUiState.Success(
            pokemonList = list.map { pokemon ->
                pokemon.copy(name = names[pokemon.id] ?: "")
            },
            offset = list.size,
            shortcuts = SHORTCUTS
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )
}