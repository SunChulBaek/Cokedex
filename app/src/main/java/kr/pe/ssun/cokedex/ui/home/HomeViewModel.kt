package kr.pe.ssun.cokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.pe.ssun.cokedex.domain.GetPokemonListParam
import kr.pe.ssun.cokedex.domain.GetSpeciesUseCase
import kr.pe.ssun.cokedex.domain.GetPokemonListUseCase
import kr.pe.ssun.cokedex.model.Species
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase,
    getSpeciesUseCase: GetSpeciesUseCase,
    pagingSource: HomePagingSource,
) : ViewModel() {

    companion object {
        const val DUMMY_ID = 0

        val SHORTCUTS = listOf(
            778, // 따라큐
            10044, // 메가뮤츠Y
            10196, // 리자몽 거다이맥스
            10157, // 울트라 네크로즈마 (진화 없음)
            936, // 카디나르마 (1단계, 2분기)
            135, // 쥬피썬더 (1단계, 8분기)
            269, // 독케일 (2단계, 1단계 분기)
            792, // 루나아라 (2단계, 2단계 분기)
        ) // 다양한 진화 형태..
    }

    val namesIds = MutableStateFlow(listOf(DUMMY_ID))

    val namesFlow = namesIds.flatMapConcat { it.asFlow() }
        .map { nameId ->
            getSpeciesUseCase(nameId).first().getOrNull() ?: Species(DUMMY_ID)
        }

    private val names = HashMap<Int, String>()

    val pagingFlow = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { pagingSource }
    ).flow

    val search = MutableStateFlow("")

    val searchResult = search.debounce(300).map { search ->
        getPokemonListUseCase(GetPokemonListParam(search = search)).first().getOrNull() ?: listOf()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf()
    )
}