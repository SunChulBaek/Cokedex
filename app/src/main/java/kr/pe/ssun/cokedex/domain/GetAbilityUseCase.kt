package kr.pe.ssun.cokedex.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.model.Ability
import kr.pe.ssun.cokedex.util.IoDispatcher
import javax.inject.Inject

class GetAbilityUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<Int, Ability>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<Ability>> =
        repository.getAbility(parameters)
            .map {
                Result.success(it)
            }
}