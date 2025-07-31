package com.arkhe.data.repository.theme

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getTheme(): Flow<Boolean?>
    suspend fun saveTheme(isDarkTheme: Boolean)
    fun hasUserMadeThemeChoice(): Flow<Boolean>
}