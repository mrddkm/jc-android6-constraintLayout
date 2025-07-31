package com.arkhe.domain.usecase.theme

import com.arkhe.data.repository.theme.ThemeRepository

class SaveThemeUseCase(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(isDarkTheme: Boolean) {
        themeRepository.saveTheme(isDarkTheme)
    }
}