package com.arkhe.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "theme_settings"
)

class ThemeDataStore(private val context: Context) {
    companion object {
        val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
        val USER_THEME_CHOICE_MADE_KEY = booleanPreferencesKey("user_theme_choice_made")
    }

    val isDarkThemeFlow: Flow<Boolean?> = context.themeDataStore.data
        .map { preferences ->
            preferences[IS_DARK_THEME_KEY]
        }

    val userThemeChoiceMadeFlow: Flow<Boolean> = context.themeDataStore.data
        .map { preferences ->
            preferences[USER_THEME_CHOICE_MADE_KEY] ?: false
        }

    suspend fun saveThemePreference(isDarkTheme: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[IS_DARK_THEME_KEY] = isDarkTheme
            preferences[USER_THEME_CHOICE_MADE_KEY] = true
        }
    }
}