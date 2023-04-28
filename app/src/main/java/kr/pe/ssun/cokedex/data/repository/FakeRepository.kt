package kr.pe.ssun.cokedex.data.repository

import kr.pe.ssun.cokedex.data.model.Photo
import kr.pe.ssun.cokedex.network.SsunNetworkDataSource
import kr.pe.ssun.cokedex.network.model.NetworkPhoto
import kr.pe.ssun.cokedex.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRepository @Inject constructor(
    private val network: SsunNetworkDataSource
) {

    fun getPhotos(): Flow<List<Photo>> = flow {
        emit(network.getPhotos().map(NetworkPhoto::asExternalModel))
    }
}