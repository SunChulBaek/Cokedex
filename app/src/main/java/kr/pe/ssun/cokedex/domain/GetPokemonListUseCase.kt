package kr.pe.ssun.cokedex.domain

import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.data.model.Pokemon
import kr.pe.ssun.cokedex.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class GetPokemonListParam(
    val limit: Int? = null,
    val offset: Int? = null,
)

class GetPokemonListUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: PokemonRepository
) : FlowUseCase<GetPokemonListParam?, List<Pokemon>>(dispatcher) {

    override fun execute(parameters: GetPokemonListParam?): Flow<Result<List<Pokemon>>> =
        repository.getPokemonList(
            limit = parameters?.limit,
            offset = parameters?.offset
        ).map { pokemonList ->
            Result.success(pokemonList)
        }
}