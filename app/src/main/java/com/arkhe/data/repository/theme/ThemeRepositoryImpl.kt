package com.arkhe.data.repository.theme

import com.arkhe.data.local.datastore.ThemeDataSource
import com.arkhe.domain.model.ThemeMode
import com.arkhe.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class ThemeRepositoryImpl(
    private val localDataSource: ThemeDataSource
) : ThemeRepository {

    override fun getThemeMode(): Flow<ThemeMode> {
        return localDataSource.getThemeMode()
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        localDataSource.setThemeMode(themeMode)
    }
}