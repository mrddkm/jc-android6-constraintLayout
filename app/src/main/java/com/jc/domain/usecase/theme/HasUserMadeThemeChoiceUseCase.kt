package com.jc.domain.usecase.theme

import com.jc.data.repository.theme.ThemeRepository
import kotlinx.coroutines.flow.Flow

class HasUserMadeThemeChoiceUseCase(
    private val themeRepository: ThemeRepository
) {
    operator fun invoke(): Flow<Boolean> = themeRepository.hasUserMadeThemeChoice()
}