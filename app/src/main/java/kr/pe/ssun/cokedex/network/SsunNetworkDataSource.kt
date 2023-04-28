package kr.pe.ssun.cokedex.network

import kr.pe.ssun.cokedex.network.model.NetworkPhoto

interface SsunNetworkDataSource {
    suspend fun getPhotos(): List<NetworkPhoto>
}