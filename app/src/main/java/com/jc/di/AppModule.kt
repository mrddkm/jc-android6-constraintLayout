package com.jc.di

import com.jc.data.local.datastore.ThemeDataStore
import com.jc.data.repository.theme.ThemeRepository
import com.jc.data.repository.theme.ThemeRepositoryImpl
import com.jc.domain.usecase.theme.GetThemeUseCase
import com.jc.domain.usecase.theme.HasUserMadeThemeChoiceUseCase
import com.jc.domain.usecase.theme.SaveThemeUseCase
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

val appModules = listOf(
    themeModule
)
