package kr.pe.ssun.cokedex.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.first
import kr.pe.ssun.cokedex.data.repository.PokemonRepository
import kr.pe.ssun.cokedex.model.Pokemon
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class HomePagingSource @Inject constructor(
    private val repository: PokemonRepository,
) : PagingSource<Int, Pokemon>() {

    companion object {
        private const val LIMIT = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val page = params.key
            val offset = if (page != null) page * LIMIT else null
            val limit = offset?.let { LIMIT } ?: run { null }
            val response = repository.getPokemonList(offset = offset, limit = limit, search = null).first()
            Timber.d("[sunchulbaek] HomePagingSource(offset = $offset) result = ${response.map { it.id }}")
            LoadResult.Page(
                data = response,
                prevKey = page?.minus(response.size / LIMIT),
                nextKey = if (response.size < LIMIT) null else (page ?: 0).plus(response.size / LIMIT),
            )
        } catch (e: Exception) {
            e.stackTrace.forEach {
                Timber.e("[sunchulbaek] $it")
            }
            LoadResult.Error(e)
        }
    }

    override val keyReuseSupported: Boolean = true
}