package com.arkhe.domain.repository

import com.arkhe.domain.model.ConnectionState
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun getConnectionState(): Flow<ConnectionState>
    suspend fun checkServerConnection(): Boolean
}