package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.model.Name
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Inject

class GetNameUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Int, Name>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<Name>> =
        repository.getName(parameters)
            .map {
                Result.success(it)
            }
}