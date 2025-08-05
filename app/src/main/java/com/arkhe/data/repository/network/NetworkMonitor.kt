package com.arkhe.data.repository.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.arkhe.domain.model.NetMonState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkMonitor {

    private val context: Context

    constructor(context: Context) {
        this.context = context
        this.connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val connectivityManager: ConnectivityManager

    fun networkState(): Flow<NetMonState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val state = getCurrentNetworkState()
                trySend(state)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(NetMonState.NotConnectedToInternet)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val state = getCurrentNetworkState()
                trySend(state)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        // Send initial state
        val initialState = getCurrentNetworkState()
        trySend(initialState)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    private fun getCurrentNetworkState(): NetMonState {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return when {
            networkCapabilities == null -> NetMonState.NotConnectedToInternet
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    NetMonState.ConnectedWifi
                } else {
                    NetMonState.NotConnectedToServer
                }
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    NetMonState.ConnectedMobileData
                } else {
                    NetMonState.NotConnectedToServer
                }
            }
            else -> NetMonState.NotConnectedToInternet
        }
    }
}