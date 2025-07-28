package com.jc

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.jc.di.appModules
import com.jc.presentation.viewmodel.LanguageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent

class Arkhe : Application() {
    // Create application scope for coroutines
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        Log.d("Arkhe", "Application onCreate started")

        // Initialize Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Arkhe)
            modules(appModules)
        }

        // Apply initial locale asynchronously
        applicationScope.launch {
            try {
                val koin = KoinJavaComponent.getKoin()
                val languageViewModel: LanguageViewModel = koin.get()

                Log.d("Arkhe", "Application onCreate - Calling getCurrentLanguageDisplay()")
                languageViewModel.getCurrentLanguageDisplay()

                // Log the result after a small delay
                kotlinx.coroutines.delay(300)
                val finalLocales = AppCompatDelegate.getApplicationLocales()
                Log.d(
                    "Arkhe",
                    "Application onCreate - Final locales after applyInitialLocale: ${finalLocales.toLanguageTags()}"
                )
            } catch (e: Exception) {
                Log.e("Arkhe", "Error during initial locale application", e)
            }
        }

        Log.d("Arkhe", "Application onCreate completed")
    }
}