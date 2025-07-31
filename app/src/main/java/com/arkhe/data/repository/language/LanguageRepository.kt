package com.arkhe.data.repository.language

import com.arkhe.data.local.datastore.LanguageDataStore
import kotlinx.coroutines.flow.Flow

class LanguageRepository(
    private val languageDataStore: LanguageDataStore
) {
    val selectedLanguageCode: Flow<String> = languageDataStore.selectedLanguageCode

    suspend fun setLanguage(languageCode: String) {
        languageDataStore.setLanguageCode(languageCode)
    }
}