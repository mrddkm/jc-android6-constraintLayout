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
import androidx.compose.ui.tooling.preview.Preview
import com.jc.presentation.ui.theme.AppSize
import com.jc.presentation.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsProfileBottomSheet(
    onDismissRequest: () -> Unit,
    isTablet: Boolean,
    showUserProfile: Boolean = false,
    onLanguageChange: (String) -> Unit = {}
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

            val settingsOptions = mutableListOf<String>()
            if (showUserProfile) {
                settingsOptions.add("User Profile")
            }
            settingsOptions.add("Bahasa (Indonesia)")
            settingsOptions.add("Bahasa (English)")

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(settingsOptions) { setting ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                                when (setting) {
                                    "Bahasa (Indonesia)" -> onLanguageChange("id")
                                    "Bahasa (English)" -> onLanguageChange("en")
                                    "User Profile" -> { /* TODO: Add detail user profile */
                                    }
                                }
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

@Preview(
    name = "SUNMI V1s",
    widthDp = 360,
    heightDp = 640,
    showBackground = true,
)
@Composable
fun ActivationScreenPreview() {
    AppTheme(darkTheme = false) {
        SettingsProfileBottomSheet(
            onDismissRequest = {},
            isTablet = false,
            showUserProfile = true,
            onLanguageChange = {}
        )
    }
}