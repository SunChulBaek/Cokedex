package kr.pe.ssun.cokedex.domain

import kr.pe.ssun.cokedex.data.repository.FakeRepository
import kr.pe.ssun.cokedex.data.model.Photo
import kr.pe.ssun.cokedex.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: FakeRepository
) : FlowUseCase<Any?, List<Photo>>(dispatcher) {

    override fun execute(parameters: Any?): Flow<Result<List<Photo>>> =
        repository.getPhotos().map { photos ->
            Result.success(photos)
        }
}