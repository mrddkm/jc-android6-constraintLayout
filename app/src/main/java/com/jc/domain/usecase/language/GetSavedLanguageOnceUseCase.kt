package com.jc.domain.usecase.language

import com.jc.data.repository.language.LanguageRepository

class GetSavedLanguageOnceUseCase(private val repository: LanguageRepository) {
    suspend operator fun invoke(): String = repository.getSelectedLanguageOnce()
}