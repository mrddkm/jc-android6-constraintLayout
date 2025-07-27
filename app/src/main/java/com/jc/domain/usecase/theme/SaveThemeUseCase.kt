package com.jc.domain.usecase.theme

import com.jc.data.repository.theme.ThemeRepository

class SaveThemeUseCase(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(isDarkTheme: Boolean) {
        themeRepository.saveTheme(isDarkTheme)
    }
}