package com.jc.presentation.ui.screens.shared.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.jc.presentation.ui.theme.AppSize
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    onDismissRequest: () -> Unit,
    isTablet: Boolean
) {
    val appSize = AppSize(isTablet = isTablet)
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = appSize.verticalPadding / 2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // You can add a drag handle indicator here if desired
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(appSize.horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                fontSize = appSize.titleSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = appSize.verticalPadding)
            )

            // Example settings options
            val settingsOptions = listOf(
                "Account Settings",
                "Notification Preferences",
                "Privacy Policy",
                "Help & Support"
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(settingsOptions) { setting ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Handle setting item click
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                                // You can add specific actions based on the clicked setting
                            }
                            .padding(vertical = appSize.verticalPadding / 2),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = setting,
                            fontSize = appSize.bodyTextSize
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(appSize.verticalPadding))
        }
    }
}
