package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.model.EvolutionChains
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Inject

class GetEvolutionChainUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Int, EvolutionChains>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<EvolutionChains>> =
        repository.getEvolutionChain(parameters)
            .map {
                Result.success(it)
            }
}