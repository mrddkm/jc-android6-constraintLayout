package com.arkhe.presentation.ui.screens.shared.ext

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.arkhe.domain.model.NetMonState
import com.arkhe.presentation.ui.theme.AppTheme

@Composable
fun NetMonDialog(
    onDismissRequest: () -> Unit,
    netMonState: NetMonState,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Icon(
                    imageVector = netMonState.icon,
                    contentDescription = null,
                    tint = netMonState.color,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Connection Status",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Connection message
                Text(
                    text = netMonState.message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = netMonState.color,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Close button
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = netMonState.color
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tutup",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun NetMonInformation(
    netMonState: NetMonState,
    isTablet: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = netMonState.icon,
            contentDescription = null,
            tint = netMonState.color,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = netMonState.message,
            fontSize = if (isTablet) 16.sp else 14.sp,
            color = netMonState.color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(name = "NetMonDialog - Connected", showBackground = true)
@Composable
fun NetMonDialogPreviewConnected() {
    AppTheme(darkTheme = true) {
        NetMonDialog(
            onDismissRequest = {},
            netMonState = NetMonState.ConnectedMobileData,
        )
    }
}