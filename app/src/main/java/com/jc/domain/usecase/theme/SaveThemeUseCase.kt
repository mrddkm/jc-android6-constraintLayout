package com.jc.domain.usecase.theme

import com.jc.data.repository.theme.ThemeRepository

class SaveThemeUseCase(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(isDarkTheme: Boolean) {
        println("SaveThemeUseCase: invoke isDarkTheme = $isDarkTheme")
        themeRepository.saveTheme(isDarkTheme)
    }
}