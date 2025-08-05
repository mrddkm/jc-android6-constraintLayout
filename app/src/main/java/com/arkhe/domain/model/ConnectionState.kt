package com.arkhe.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ConnectionState(
    val message: String,
    val icon: ImageVector,
    val color: Color
) {
    object ConnectedMobileData : ConnectionState(
        message = "Terhubung ke Internet via Data Seluler",
        icon = Icons.Default.SignalCellularAlt,
        color = Color(0xFF4CAF50) // Green
    )

    object ConnectedWifi : ConnectionState(
        message = "Terhubung ke Internet via WiFi",
        icon = Icons.Default.Wifi,
        color = Color(0xFF2196F3) // Blue
    )

    object NotConnectedToInternet : ConnectionState(
        message = "Tidak Terhubung ke Internet",
        icon = Icons.Default.SignalWifiOff,
        color = Color(0xFFFF9800) // Orange
    )

    object NotConnectedToServer : ConnectionState(
        message = "Tidak Terhubung ke Server",
        icon = Icons.Default.CloudOff,
        color = Color(0xFFE91E63) // Pink
    )
}