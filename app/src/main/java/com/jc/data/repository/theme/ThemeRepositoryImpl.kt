package com.jc.data.repository.theme

import com.jc.data.local.datastore.ThemeDataStore
import kotlinx.coroutines.flow.Flow

class ThemeRepositoryImpl(
    private val themeDataStore: ThemeDataStore
) : ThemeRepository {
    override fun getTheme(): Flow<Boolean?> = themeDataStore.isDarkThemeFlow
    override suspend fun saveTheme(isDarkTheme: Boolean) {
        try {
            themeDataStore.saveThemePreference(isDarkTheme)
        } catch (e: Exception) {
            throw e
        }
    }
    override fun hasUserMadeThemeChoice(): Flow<Boolean> = themeDataStore.userThemeChoiceMadeFlow
}
