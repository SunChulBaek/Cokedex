package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.network.model.NetworkMove
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Inject

class GetMovesUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Int, NetworkMove>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<NetworkMove>> =
        repository.getMove(parameters)
            .map {
                Result.success(it)
            }
}