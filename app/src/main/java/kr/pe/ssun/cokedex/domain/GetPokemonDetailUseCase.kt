package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.model.PokemonDetail
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.util.IoDispatcher
import timber.log.Timber
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Int, PokemonDetail>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<PokemonDetail>> =
        repository.getPokemonDetail(parameters)
            .map { pokemonDetail ->
                Result.success(pokemonDetail)
            }
}