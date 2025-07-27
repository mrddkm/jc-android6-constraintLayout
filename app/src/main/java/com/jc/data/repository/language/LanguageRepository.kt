package com.jc.data.repository.language

import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getSelectedLanguageFlow(): Flow<String>
    suspend fun saveSelectedLanguage(languageCode: String)
    suspend fun getSelectedLanguageOnce(): String
}