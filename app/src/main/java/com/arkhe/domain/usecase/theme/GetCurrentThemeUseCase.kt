package com.arkhe.domain.usecase.theme

import com.arkhe.domain.model.ThemeMode
import com.arkhe.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentThemeUseCase(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<ThemeMode> = repository.getThemeMode()
}