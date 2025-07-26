package com.jc.data.repository.theme

import com.jc.data.local.datastore.ThemeDataStore
import kotlinx.coroutines.flow.Flow

class ThemeRepositoryImpl(
    private val themeDataStore: ThemeDataStore
) : ThemeRepository {
    override fun getTheme(): Flow<Boolean?> = themeDataStore.isDarkThemeFlow
    override suspend fun saveTheme(isDarkTheme: Boolean) {
        println("ThemeRepositoryImpl: saveTheme isDarkTheme = $isDarkTheme")
        try {
            themeDataStore.saveThemePreference(isDarkTheme)
            println("ThemeRepositoryImpl: themeDataStore.saveThemePreference success.")
        } catch (e: Exception) {
            println("ThemeRepositoryImpl: Error saat saveThemePreference: ${e.message}")
        }
    }
    override fun hasUserMadeThemeChoice(): Flow<Boolean> = themeDataStore.userThemeChoiceMadeFlow
}
