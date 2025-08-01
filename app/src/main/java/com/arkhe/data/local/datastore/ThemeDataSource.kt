package com.arkhe.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arkhe.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeDataSource(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme_preferences")
        private val THEME_KEY = stringPreferencesKey("theme_mode")
    }

    fun getThemeMode(): Flow<ThemeMode> = context.dataStore.data.map { preferences ->
        val themeName = preferences[THEME_KEY] ?: ThemeMode.AUTOMATIC.name
        ThemeMode.valueOf(themeName)
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = themeMode.name
        }
    }
}