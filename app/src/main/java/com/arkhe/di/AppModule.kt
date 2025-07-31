package com.arkhe.di

import com.arkhe.data.local.datastore.LanguageDataStore
import com.arkhe.data.local.datastore.ThemeDataStore
import com.arkhe.data.repository.language.LanguageRepository
import com.arkhe.data.repository.theme.ThemeRepository
import com.arkhe.data.repository.theme.ThemeRepositoryImpl
import com.arkhe.domain.usecase.theme.GetThemeUseCase
import com.arkhe.domain.usecase.theme.HasUserMadeThemeChoiceUseCase
import com.arkhe.domain.usecase.theme.SaveThemeUseCase
import com.arkhe.presentation.viewmodel.LanguageViewModel
import com.arkhe.presentation.viewmodel.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // DataStore instances
    single { ThemeDataStore(androidContext()) }
    single { LanguageDataStore(androidContext()) }

    // Repositories
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }
    single { LanguageRepository(get()) } // Hanya LanguageDataStore

    // Theme Use Cases
    factory { GetThemeUseCase(get()) }
    factory { SaveThemeUseCase(get()) }
    factory { HasUserMadeThemeChoiceUseCase(get()) }

    // ViewModels
    viewModel { ThemeViewModel(get(), get(), get()) }
    viewModel { LanguageViewModel(get(), get()) }
}