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
import kr.pe.ssun.cokedex.model.Name
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase,
    getNameUseCase: GetNameUseCase,
) : ViewModel() {

    companion object {
        const val DUMMY_ID = 0
    }

    val param = MutableStateFlow<GetPokemonListParam?>(null)

    private val pokemonListFlow = param.flatMapConcat { param ->
        getPokemonListUseCase(param)
    }

    private val namesIds = MutableStateFlow(listOf(DUMMY_ID))

    private val namesFlow = namesIds.flatMapConcat { it.asFlow() }
        .map { nameId ->
            getNameUseCase(nameId).first().getOrNull() ?: Name(DUMMY_ID)
        }

    private val list = mutableListOf<Pokemon>()
    private val names = HashMap<Int, String>()

    val uiState = combine(
        pokemonListFlow,
        namesFlow
    ) { pokemonListResult, newName ->
        // 이름 정보 업뎃
        if (!names.contains(newName.id) && newName.name != null) {
            Timber.d("[sunchulbaek] name(id = ${newName.id}) 업뎃 = ${newName.name}")
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
            offset = list.size
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )
}