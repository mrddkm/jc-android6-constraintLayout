package com.jc.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.jc.core.util.Constants
import com.jc.core.util.LocaleHelper

data class LanguageOption(val code: String, val displayName: String)

class LanguageViewModel(
    private val context: Context
) : ViewModel() {

    init {
        Log.d("LanguageViewModel", "LanguageViewModel initialized: ${this.hashCode()}")
    }

    val supportedLanguages = listOf(
        LanguageOption(Constants.LANGUAGE_CODE_ENGLISH, Constants.LANGUAGE_DISPLAY_NAME_ENGLISH),
        LanguageOption(
            Constants.LANGUAGE_CODE_INDONESIAN,
            Constants.LANGUAGE_DISPLAY_NAME_INDONESIAN
        )
    )

    fun getCurrentLanguage(): String {
        return LocaleHelper.getLanguage(context)
    }

    fun getCurrentLanguageDisplay(): String {
        val currentCode = getCurrentLanguage()
        return supportedLanguages.find { it.code == currentCode }?.displayName
            ?: Constants.LANGUAGE_DISPLAY_NAME_ENGLISH
    }
}

/*
data class LanguageOption(val code: String, val displayName: String)

class LanguageViewModel(
    private val saveLanguageUseCase: SaveLanguageUseCase,
    getSelectedLanguageFlowUseCase: GetSelectedLanguageFlowUseCase,
    private val getSavedLanguageOnceUseCase: GetSavedLanguageOnceUseCase
) : ViewModel() {

    init {
        Log.d("LanguageViewModel", "LanguageViewModel initialized: ${this.hashCode()}")
    }

    val currentLanguageCode: StateFlow<String> =
        getSelectedLanguageFlowUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Constants.DEFAULT_LANGUAGE_CODE
            )

    val supportedLanguages = listOf(
        LanguageOption(Constants.LANGUAGE_CODE_ENGLISH, Constants.LANGUAGE_DISPLAY_NAME_ENGLISH),
        LanguageOption(
            Constants.LANGUAGE_CODE_INDONESIAN,
            Constants.LANGUAGE_DISPLAY_NAME_INDONESIAN
        )
    )

    fun setLanguage(languageCode: String, onComplete: () -> Unit) {
        if (currentLanguageCode.value == languageCode) {
            Log.d("LanguageViewModel", "Language already set to: $languageCode")
            return
        }

        viewModelScope.launch {
            try {
                Log.d("LanguageViewModel", "Starting language change to: $languageCode")

                // 1. Save to DataStore first
                Log.d("LanguageViewModel", "Saving language to DataStore: $languageCode")
                saveLanguageUseCase(languageCode)

                // 2. Wait for DataStore to complete
                delay(300)

                // 3. Apply system locale
                Log.d("LanguageViewModel", "Setting application locales: $languageCode")
                val localeList = LocaleListCompat.forLanguageTags(languageCode)
                AppCompatDelegate.setApplicationLocales(localeList)

                // 4. Wait for locale to be applied
                delay(500)

                // 5. Verify the change
                val currentLocales = AppCompatDelegate.getApplicationLocales()
                Log.d(
                    "LanguageViewModel",
                    "Language change completed. Current locales: ${currentLocales.toLanguageTags()}"
                )

                // 6. Trigger completion callback
                onComplete()

            } catch (e: Exception) {
                Log.e("LanguageViewModel", "Error setting language", e)
            }
        }
    }

    fun applyInitialLocale() {
        viewModelScope.launch {
            try {
                // Add delay to ensure DataStore is ready
                delay(100)

                val savedLanguage = getSavedLanguageOnceUseCase()
                Log.d(
                    "LanguageViewModel",
                    "applyInitialLocale called. Saved language from DataStore: $savedLanguage"
                )

                val currentAppLocales = AppCompatDelegate.getApplicationLocales()
                val currentLanguageTags = currentAppLocales.toLanguageTags()

                Log.d(
                    "LanguageViewModel",
                    "Current system app locales: $currentLanguageTags"
                )

                // Only apply if different or empty
                val needsUpdate = currentAppLocales.isEmpty ||
                        currentLanguageTags.isEmpty() ||
                        !currentLanguageTags.contains(savedLanguage)

                if (needsUpdate) {
                    Log.d("LanguageViewModel", "Applying initial locales: $savedLanguage")
                    val localeList = LocaleListCompat.forLanguageTags(savedLanguage)
                    AppCompatDelegate.setApplicationLocales(localeList)

                    // Wait for the locale to be applied
                    delay(200)

                    Log.d(
                        "LanguageViewModel",
                        "Initial locale applied. New locales: ${
                            AppCompatDelegate.getApplicationLocales().toLanguageTags()
                        }"
                    )
                } else {
                    Log.d(
                        "LanguageViewModel",
                        "No change needed, current locales already match: $savedLanguage"
                    )
                }
            } catch (e: Exception) {
                Log.e("LanguageViewModel", "Error applying initial locale", e)
                // Fallback to default language
                try {
                    val localeList = LocaleListCompat.forLanguageTags(Constants.DEFAULT_LANGUAGE_CODE)
                    AppCompatDelegate.setApplicationLocales(localeList)
                } catch (fallbackError: Exception) {
                    Log.e("LanguageViewModel", "Error applying fallback locale", fallbackError)
                }
            }
        }
    }
}
*/
