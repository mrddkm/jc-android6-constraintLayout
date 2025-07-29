package com.jc.data.repository.language

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jc.model.language.Languages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "language_settings")

class LanguageRepository(private val context: Context) {

    private val languageCodeKey = stringPreferencesKey("language_code")

    val selectedLanguageCode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[languageCodeKey] ?: Languages.ENGLISH.code
        }

    suspend fun setLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[languageCodeKey] = languageCode
        }
    }
}