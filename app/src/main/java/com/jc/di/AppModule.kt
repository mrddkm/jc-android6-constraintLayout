package com.jc.di

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
    single { ThemeDataStore(androidContext()) }
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }
    single { LanguageRepository(get()) }

    factory { GetThemeUseCase(get()) }
    factory { SaveThemeUseCase(get()) }
    factory { HasUserMadeThemeChoiceUseCase(get()) }

    viewModel { ThemeViewModel(get(), get(), get()) }
    viewModel { LanguageViewModel(get(), get()) }
}