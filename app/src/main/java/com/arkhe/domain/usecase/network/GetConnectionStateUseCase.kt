package com.arkhe.domain.usecase.network

import com.arkhe.domain.model.ConnectionState
import com.arkhe.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow

class GetConnectionStateUseCase(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Flow<ConnectionState> {
        return networkRepository.getConnectionState()
    }
}