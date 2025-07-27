package com.jc

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.jc.di.appModules
import com.jc.presentation.viewmodel.LanguageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent

class Arkhe : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Arkhe)
            modules(appModules)
        }
        val koin = KoinJavaComponent.getKoin()
        val languageViewModel: LanguageViewModel = koin.get()

        Log.d("Arkhe", "Application onCreate - Calling applyInitialLocale()")
        languageViewModel.applyInitialLocale()
        Log.d(
            "Arkhe",
            "Application onCreate - Locales after applyInitialLocale: ${
                AppCompatDelegate.getApplicationLocales().toLanguageTags()
            }"
        )
    }
}
