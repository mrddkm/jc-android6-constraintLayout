package com.arkhe.presentation.state

import com.arkhe.domain.model.ThemeMode


data class ThemeUiState(
    val currentTheme: ThemeMode = ThemeMode.AUTOMATIC,
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val selectedUIType: ThemeUIType = ThemeUIType.BOTTOM_SHEET
)

enum class ThemeUIType(val displayName: String) {
    BOTTOM_SHEET("BottomSheet Selector"),
    CYCLING_BUTTON("Cycling Button"),
    THREE_BUTTONS("Three Buttons Row")
}