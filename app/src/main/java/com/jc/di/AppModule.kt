package com.jc.di

import com.jc.data.local.datastore.LanguageDataStore
import com.jc.data.local.datastore.ThemeDataStore
import com.jc.data.repository.language.LanguageRepository
import com.jc.data.repository.language.LanguageRepositoryImpl
import com.jc.data.repository.theme.ThemeRepository
import com.jc.data.repository.theme.ThemeRepositoryImpl
import com.jc.domain.usecase.language.GetSavedLanguageOnceUseCase
import com.jc.domain.usecase.language.GetSelectedLanguageFlowUseCase
import com.jc.domain.usecase.language.SaveLanguageUseCase
import com.jc.domain.usecase.theme.GetThemeUseCase
import com.jc.domain.usecase.theme.HasUserMadeThemeChoiceUseCase
import com.jc.domain.usecase.theme.SaveThemeUseCase
import com.jc.presentation.viewmodel.LanguageViewModel
import com.jc.presentation.viewmodel.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("DEPRECATION")
val themeModule = module {
    single { ThemeDataStore(androidContext()) }
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }

    factory { GetThemeUseCase(get()) }
    factory { SaveThemeUseCase(get()) }
    factory { HasUserMadeThemeChoiceUseCase(get()) }

    viewModel { ThemeViewModel(get(), get(), get()) }
}

@Suppress("DEPRECATION")
val languageModule = module {
    single { LanguageDataStore(androidContext()) }
    single<LanguageRepository> { LanguageRepositoryImpl(get()) }

    factory { SaveLanguageUseCase(get()) }
    factory { GetSelectedLanguageFlowUseCase(get()) }
    factory { GetSavedLanguageOnceUseCase(get()) }

    viewModel { LanguageViewModel(get()) }
}

val appModules = listOf(
    themeModule,
    languageModule
)
