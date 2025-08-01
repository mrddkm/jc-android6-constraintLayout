package com.arkhe.domain.usecase.theme

import com.arkhe.domain.model.ThemeMode
import com.arkhe.domain.repository.ThemeRepository

class SetThemeUseCase(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(themeMode: ThemeMode) {
        repository.setThemeMode(themeMode)
    }
}