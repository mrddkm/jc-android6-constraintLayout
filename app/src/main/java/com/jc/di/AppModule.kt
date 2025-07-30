package com.jc.di

import com.jc.data.local.datastore.LanguageDataStore
import com.jc.data.local.datastore.ThemeDataStore
import com.jc.data.repository.language.LanguageRepository
import com.jc.data.repository.theme.ThemeRepository
import com.jc.data.repository.theme.ThemeRepositoryImpl
import com.jc.domain.usecase.theme.GetThemeUseCase
import com.jc.domain.usecase.theme.HasUserMadeThemeChoiceUseCase
import com.jc.domain.usecase.theme.SaveThemeUseCase
import com.jc.presentation.viewmodel.LanguageViewModel
import com.jc.presentation.viewmodel.ThemeViewModel
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