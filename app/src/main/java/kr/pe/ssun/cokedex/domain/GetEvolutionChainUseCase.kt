package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.model.EvolutionChain
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Inject

class GetEvolutionChainUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Int, EvolutionChain>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<EvolutionChain>> =
        repository.getEvolutionChain(parameters)
            .map {
                Result.success(it)
            }
}