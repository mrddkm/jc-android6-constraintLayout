package com.arkhe.domain.repository

import com.arkhe.domain.model.NetMonState
import kotlinx.coroutines.flow.Flow

interface NetMonRepository {
    fun getConnectionState(): Flow<NetMonState>
    suspend fun checkServerConnection(): Boolean
}