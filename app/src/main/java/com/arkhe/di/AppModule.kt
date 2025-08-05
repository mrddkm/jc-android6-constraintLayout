package com.arkhe.di

import com.arkhe.data.local.datastore.LanguageDataStore
import com.arkhe.data.local.datastore.ThemeDataSource
import com.arkhe.data.repository.language.LanguageRepository
import com.arkhe.data.repository.network.NetworkMonitor
import com.arkhe.data.repository.network.NetMonRepositoryImpl
import com.arkhe.data.repository.theme.ThemeRepositoryImpl
import com.arkhe.domain.repository.NetMonRepository
import com.arkhe.domain.repository.ThemeRepository
import com.arkhe.domain.usecase.netmon.GetNetMonStateUseCase
import com.arkhe.domain.usecase.theme.GetCurrentThemeUseCase
import com.arkhe.domain.usecase.theme.SetThemeUseCase
import com.arkhe.presentation.viewmodel.AboutDialogViewModel
import com.arkhe.presentation.viewmodel.NetMonViewModel
import com.arkhe.presentation.viewmodel.LanguageViewModel
import com.arkhe.presentation.viewmodel.SettingsProfileViewModel
import com.arkhe.presentation.viewmodel.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { LanguageDataStore(androidContext()) }
    single { ThemeDataSource(get()) }
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }
    single { LanguageRepository(get()) }
    single { NetworkMonitor(get()) }
    single<NetMonRepository> { NetMonRepositoryImpl(get()) }
}

val domainModule = module {
    factory { GetCurrentThemeUseCase(get()) }
    factory { SetThemeUseCase(get()) }
    single { GetNetMonStateUseCase(get()) }
}

val presentationModule = module {
    viewModel { AboutDialogViewModel() }
    viewModel { SettingsProfileViewModel() }
    viewModel { ThemeViewModel(get(), get()) }
    viewModel { LanguageViewModel(get(), get()) }
    viewModel { NetMonViewModel(get()) }
}

val appModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)