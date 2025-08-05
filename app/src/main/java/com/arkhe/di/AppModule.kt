package com.arkhe.di

import com.arkhe.data.local.datastore.LanguageDataStore
import com.arkhe.data.local.datastore.ThemeDataSource
import com.arkhe.data.repository.language.LanguageRepository
import com.arkhe.data.repository.network.NetworkMonitor
import com.arkhe.data.repository.network.NetworkRepositoryImpl
import com.arkhe.data.repository.theme.ThemeRepositoryImpl
import com.arkhe.domain.repository.NetworkRepository
import com.arkhe.domain.repository.ThemeRepository
import com.arkhe.domain.usecase.network.GetConnectionStateUseCase
import com.arkhe.domain.usecase.theme.GetCurrentThemeUseCase
import com.arkhe.domain.usecase.theme.SetThemeUseCase
import com.arkhe.presentation.viewmodel.ConnectionViewModel
import com.arkhe.presentation.viewmodel.LanguageViewModel
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
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
}

val domainModule = module {
    factory { GetCurrentThemeUseCase(get()) }
    factory { SetThemeUseCase(get()) }
    single { GetConnectionStateUseCase(get()) }
}

val presentationModule = module {
    viewModel { ThemeViewModel(get(), get()) }
    viewModel { LanguageViewModel(get(), get()) }
    viewModel { ConnectionViewModel(get()) }
}

val appModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)