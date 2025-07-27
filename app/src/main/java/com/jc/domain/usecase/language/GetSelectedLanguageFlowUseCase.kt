package com.jc.domain.usecase.language

import com.jc.data.repository.language.LanguageRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedLanguageFlowUseCase(private val repository: LanguageRepository) {
    operator fun invoke(): Flow<String> {
        return repository.getSelectedLanguageFlow()
    }
}