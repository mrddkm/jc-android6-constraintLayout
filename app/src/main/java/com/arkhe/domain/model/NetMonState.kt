package com.arkhe.domain.model

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkhe.base.R
import com.arkhe.core.utils.LanguageManager

sealed class NetMonState(
    val icon: ImageVector,
    val color: Color,
    private val messageRes: Int
) {
    object ConnectedMobileData : NetMonState(
        icon = Icons.Default.SignalCellularAlt,
        color = Color(0xFF008450),
        messageRes = R.string.connected_mobile_data
    )

    object ConnectedWifi : NetMonState(
        icon = Icons.Default.Wifi,
        color = Color(0xFF008450),
        messageRes = R.string.connected_wifi
    )

    object NotConnectedToInternet : NetMonState(
        icon = Icons.Default.SignalWifiOff,
        color = Color(0xFFEFB700),
        messageRes = R.string.not_connected_to_internet
    )

    object NotConnectedToServer : NetMonState(
        icon = Icons.Default.CloudOff,
        color = Color(0xFFB81D13),
        messageRes = R.string.not_connected_to_server
    )

    fun getMessage(context: Context, languageCode: String): String {
        return LanguageManager.getLocalizedString(context, messageRes, languageCode)
    }

    companion object {
        val allStates: List<NetMonState> = listOf(
            ConnectedMobileData,
            ConnectedWifi,
            NotConnectedToInternet,
            NotConnectedToServer
        )
    }
}