package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.model.UiPokemonDetail
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Int, UiPokemonDetail>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<UiPokemonDetail>> =
        repository.getPokemonDetail(parameters)
            .map { pokemonDetail ->
                Result.success(pokemonDetail)
            }
}