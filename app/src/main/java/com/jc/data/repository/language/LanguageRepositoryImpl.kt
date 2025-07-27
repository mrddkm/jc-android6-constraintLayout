package com.jc.data.repository.language

import com.jc.data.local.datastore.LanguageDataStore
import kotlinx.coroutines.flow.Flow

class LanguageRepositoryImpl(
    private val languageDataStore: LanguageDataStore
) : LanguageRepository {

    override fun getSelectedLanguageFlow(): Flow<String> {
        return languageDataStore.selectedLanguageFlow
    }

    override suspend fun saveSelectedLanguage(languageCode: String) {
        languageDataStore.saveSelectedLanguage(languageCode)
    }

    override suspend fun getSelectedLanguageOnce(): String {
        return languageDataStore.getSelectedLanguageOnce()
    }
}