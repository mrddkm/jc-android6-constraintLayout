package com.jc.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jc.core.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFS_NAME_LANGUAGE
)

object UserPreferenceKeys {
    val SELECTED_LANGUAGE_CODE = stringPreferencesKey(Constants.KEY_SELECTED_LANGUAGE)
}

const val DEFAULT_LANGUAGE_CODE = Constants.DEFAULT_LANGUAGE_CODE

class LanguageDataStore(private val context: Context) {
    val selectedLanguageFlow: Flow<String> = context.languageDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val lang = preferences[UserPreferenceKeys.SELECTED_LANGUAGE_CODE] ?: DEFAULT_LANGUAGE_CODE
            Log.d("UserPrefsDataStore", "DataStore Flow emitting: $lang")
            lang
        }

    suspend fun saveSelectedLanguage(languageCode: String) {
        context.languageDataStore.edit { preferences ->
            preferences[UserPreferenceKeys.SELECTED_LANGUAGE_CODE] = languageCode
            Log.d("UserPrefsDataStore", "Saved language to DataStore: $languageCode")
        }
    }

    /*
    suspend fun getSelectedLanguageOnce(): String {
        return context.userPreferencesDataStore.data.first()[UserPreferenceKeys.SELECTED_LANGUAGE_CODE] ?: DEFAULT_LANGUAGE_CODE
    }
    */
}
