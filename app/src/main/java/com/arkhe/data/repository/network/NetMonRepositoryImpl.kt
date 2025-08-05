package com.arkhe.data.repository.network

import com.arkhe.domain.model.NetMonState
import com.arkhe.domain.repository.NetMonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class NetMonRepositoryImpl(
    private val networkMonitor: NetworkMonitor
) : NetMonRepository {

    override fun getConnectionState(): Flow<NetMonState> {
        return networkMonitor.networkState().flowOn(Dispatchers.IO)
    }

    override suspend fun checkServerConnection(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://www.google.com")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connectTimeout = 3000
            connection.readTimeout = 3000
            connection.responseCode == HttpURLConnection.HTTP_OK
        } catch (_: Exception) {
            false
        }
    }
}