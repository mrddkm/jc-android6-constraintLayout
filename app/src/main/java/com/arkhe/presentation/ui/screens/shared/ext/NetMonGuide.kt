package com.arkhe.presentation.ui.screens.shared.ext

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.arkhe.domain.model.NetMonState
import com.arkhe.model.language.Language
import com.arkhe.presentation.ui.components.SourceCodePro
import com.arkhe.presentation.ui.theme.AppSize
import com.arkhe.presentation.ui.theme.AppTheme

@Composable
fun NetMonGuide(
    onDismissRequest: () -> Unit,
    currentNetMonState: NetMonState,
    isTablet: Boolean = false,
    selectedLanguage: Language,
) {
    val appSize = AppSize(isTablet = isTablet)
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = appSize.verticalPadding,
                    horizontal = appSize.horizontalPadding
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = appSize.verticalPadding,
                        horizontal = appSize.horizontalPadding
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                currentNetMonState.let { state ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(appSize.verticalPadding / 2),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 8)
                    ) {
                        Icon(
                            imageVector = state.icon,
                            contentDescription = null,
                            tint = state.color,
                            modifier = Modifier.size(appSize.iconSizeDp * 2)
                        )
                        Text(
                            text = state.getMessage(context, selectedLanguage.code),
                            fontSize = appSize.titleSize,
                            color = state.color,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(appSize.verticalPadding))
                NetMonStatusList(
                    isTablet = isTablet,
                    selectedLanguage = selectedLanguage
                )
                Spacer(modifier = Modifier.height(appSize.verticalPadding))
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "OK",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun NetMonStatusList(
    isTablet: Boolean = false,
    selectedLanguage: Language,
) {
    val appSize = AppSize(isTablet = isTablet)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(appSize.verticalPadding / 4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = NetMonState.ConnectedMobileData.icon,
                contentDescription = null,
                tint = NetMonState.ConnectedMobileData.color,
                modifier = Modifier
                    .size(appSize.iconSizeDp / 1.5f)
            )
            Text(
                text = NetMonState.ConnectedMobileData.getMessage(context, selectedLanguage.code),
                color = NetMonState.ConnectedMobileData.color,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontFamily = SourceCodePro,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = NetMonState.ConnectedWifi.icon,
                contentDescription = null,
                tint = NetMonState.ConnectedWifi.color,
                modifier = Modifier
                    .size(appSize.iconSizeDp / 1.5f)
            )
            Text(
                text = NetMonState.ConnectedWifi.getMessage(context, selectedLanguage.code),
                color = NetMonState.ConnectedWifi.color,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontFamily = SourceCodePro,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = NetMonState.NotConnectedToInternet.icon,
                contentDescription = null,
                tint = NetMonState.NotConnectedToInternet.color,
                modifier = Modifier
                    .size(appSize.iconSizeDp / 1.5f)
            )
            Text(
                text = NetMonState.NotConnectedToInternet.getMessage(
                    context,
                    selectedLanguage.code
                ),
                color = NetMonState.NotConnectedToInternet.color,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontFamily = SourceCodePro,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(appSize.fieldSpacing / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = NetMonState.NotConnectedToServer.icon,
                contentDescription = null,
                tint = NetMonState.NotConnectedToServer.color,
                modifier = Modifier
                    .size(appSize.iconSizeDp / 1.5f)
            )
            Text(
                text = NetMonState.NotConnectedToServer.getMessage(context, selectedLanguage.code),
                color = NetMonState.NotConnectedToServer.color,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontFamily = SourceCodePro,
            )
        }
    }
}

@Composable
fun NetMonInformation(
    netMonState: NetMonState,
    isTablet: Boolean = false,
    selectedLanguage: Language,
) {
    val appSize = AppSize(isTablet = isTablet)
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = netMonState.icon,
            contentDescription = null,
            tint = netMonState.color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(appSize.fieldSpacing / 2))
        Text(
            text = netMonState.getMessage(context, selectedLanguage.code),
            fontSize = if (isTablet) 16.sp else 14.sp,
            color = netMonState.color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(name = "NetMonDialog - Connected", showBackground = true)
@Composable
fun NetMonDialogPreviewConnected() {
    AppTheme(darkTheme = false) {
        NetMonGuide(
            onDismissRequest = {},
            currentNetMonState = NetMonState.ConnectedMobileData,
            selectedLanguage = Language("en", "English", "English")
        )
    }
}