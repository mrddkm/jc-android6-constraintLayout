package com.jc.presentation.viewmodel

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jc.core.util.Constants
import com.jc.domain.usecase.language.GetSelectedLanguageFlowUseCase
import com.jc.domain.usecase.language.SaveLanguageUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LanguageOption(val code: String, val displayName: String)

class LanguageViewModel(
    private val saveLanguageUseCase: SaveLanguageUseCase,
    getSelectedLanguageFlowUseCase: GetSelectedLanguageFlowUseCase,
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

    fun setLanguage(languageCode: String, activityRecreator: () -> Unit) {
        if (currentLanguageCode.value == languageCode) return

        viewModelScope.launch {
            Log.d("LanguageViewModel", "Attempting to save language: $languageCode")
            saveLanguageUseCase(languageCode)
            Log.d(
                "LanguageViewModel",
                "Language saved. New currentLanguageCode from Flow: ${currentLanguageCode.value}"
            )

            val localeList = LocaleListCompat.forLanguageTags(languageCode)
            Log.d(
                "LanguageViewModel",
                "Setting application locales to: ${localeList.toLanguageTags()}"
            )
            AppCompatDelegate.setApplicationLocales(localeList)

            Log.d("LanguageViewModel", "Calling activityRecreator callback.")
            activityRecreator()
        }
    }

    fun applyInitialLocale() {
        val languageCodeToApply = currentLanguageCode.value
        Log.d(
            "LanguageViewModel",
            "applyInitialLocale called. Language to apply from StateFlow: $languageCodeToApply"
        )
        val currentAppLocales = AppCompatDelegate.getApplicationLocales()
        Log.d(
            "LanguageViewModel",
            "Current system app locales: ${currentAppLocales.toLanguageTags()}"
        )

        if (currentAppLocales.isEmpty || currentAppLocales.toLanguageTags() != languageCodeToApply) {
            Log.d("LanguageViewModel", "Applying new locales: $languageCodeToApply")
            val localeList = LocaleListCompat.forLanguageTags(languageCodeToApply)
            AppCompatDelegate.setApplicationLocales(localeList)
        } else {
            Log.d(
                "LanguageViewModel",
                "No change needed, current locales already match: $languageCodeToApply"
            )
        }
    }
}
