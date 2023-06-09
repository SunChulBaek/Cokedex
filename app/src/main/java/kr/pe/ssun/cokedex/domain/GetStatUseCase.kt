package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.model.Stat
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Inject

class GetStatUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Pair<Int, Int>, Stat>(dispatcher) {

    override fun execute(parameters: Pair<Int, Int>): Flow<Result<Stat>> =
        repository.getStat(parameters.first, parameters.second)
            .map {
                Result.success(it)
            }
}