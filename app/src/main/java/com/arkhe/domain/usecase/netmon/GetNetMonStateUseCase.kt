package com.arkhe.domain.usecase.netmon

import com.arkhe.domain.model.NetMonState
import com.arkhe.domain.repository.NetMonRepository
import kotlinx.coroutines.flow.Flow

class GetNetMonStateUseCase(
    private val netMonRepository: NetMonRepository
) {
    operator fun invoke(): Flow<NetMonState> {
        return netMonRepository.getConnectionState()
    }
}