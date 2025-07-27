package com.jc.domain.usecase.language

import com.jc.data.repository.language.LanguageRepository

class SaveLanguageUseCase(private val repository: LanguageRepository) {
    suspend operator fun invoke(languageCode: String) {
        repository.saveSelectedLanguage(languageCode)
    }
}