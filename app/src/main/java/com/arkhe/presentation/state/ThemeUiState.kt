package com.arkhe.presentation.state

import com.arkhe.domain.model.ThemeMode

data class ThemeUiState(
    val currentTheme: ThemeMode = ThemeMode.AUTOMATIC,
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val selectedUIType: ThemeUIType = ThemeUIType.THREE_BUTTONS
)

enum class ThemeUIType() {
    THREE_BUTTONS()
}