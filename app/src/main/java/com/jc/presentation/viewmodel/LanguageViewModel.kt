package com.jc.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jc.constraintlayout.R
import com.jc.core.utils.ConsLang
import com.jc.core.utils.Constants
import com.jc.core.utils.LanguageManager
import com.jc.data.repository.language.LanguageRepository
import com.jc.model.language.Language
import com.jc.model.language.LanguageState
import com.jc.model.language.Languages
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LanguageViewModel(
    application: Application,
    private val languageRepository: LanguageRepository
) : AndroidViewModel(application) {

    private val _languageState = MutableStateFlow(
        LanguageState(
            currentLanguage = Languages.ENGLISH,
            localizedStrings = loadLocalizedStrings(Constants.DEFAULT_LANGUAGE_CODE)
        )
    )
    val languageState: StateFlow<LanguageState> = _languageState.asStateFlow()

    init {
        initializeLanguage()
    }

    private fun initializeLanguage() {
        viewModelScope.launch {
            val initialLocalizedStrings = loadLocalizedStrings(Constants.DEFAULT_LANGUAGE_CODE)
            _languageState.value = _languageState.value.copy(
                localizedStrings = initialLocalizedStrings
            )

            observeLanguageChanges()
        }
    }

    private fun observeLanguageChanges() {
        viewModelScope.launch {
            languageRepository.selectedLanguageCode.collect { languageCode ->
                val language = Languages.getLanguageByCode(languageCode)
                val localizedStrings = loadLocalizedStrings(languageCode)

                _languageState.value = _languageState.value.copy(
                    currentLanguage = language,
                    localizedStrings = localizedStrings
                )
            }
        }
    }

    fun getLocalizedString(key: String): String {
        val result = _languageState.value.localizedStrings[key]

        if (result.isNullOrEmpty()) {
            val fallbackStrings = loadLocalizedStrings(Constants.DEFAULT_LANGUAGE_CODE)
            return fallbackStrings[key] ?: key // Return key as last resort
        }

        return result
    }

    fun selectLanguage(language: Language) {
        viewModelScope.launch {
            val currentLanguage = _languageState.value.currentLanguage

            if (currentLanguage.code != language.code) {
                _languageState.value = _languageState.value.copy(isChangingLanguage = true)

                languageRepository.setLanguage(language.code)

                delay(800)

                val newLocalizedStrings = loadLocalizedStrings(language.code)

                _languageState.value = _languageState.value.copy(
                    currentLanguage = language,
                    localizedStrings = newLocalizedStrings,
                    isChangingLanguage = false
                )
            }
        }
    }

    private fun loadLocalizedStrings(languageCode: String): Map<String, String> {
        return try {
            mapOf(
                ConsLang.APP_NAME to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.app_name,
                    languageCode
                ),
                ConsLang.SELECT_LANGUAGE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.select_language,
                    languageCode
                ),
                "current_language" to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.current_language,
                    languageCode
                ),
                ConsLang.ACTIVATION_TITLE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.activation_title,
                    languageCode
                ),
                ConsLang.ACTIVATION_SUBTITLE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.activation_subtitle,
                    languageCode
                ),
                ConsLang.ACTIVATION_BUTTON to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.activate_button,
                    languageCode
                ),
                ConsLang.USERID_INPUT_PLACEHOLDER to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.user_id_placeholder,
                    languageCode
                ),
            )
        } catch (_: Exception) {
            if (languageCode != Constants.DEFAULT_LANGUAGE_CODE) {
                loadLocalizedStrings(Constants.DEFAULT_LANGUAGE_CODE)
            } else {
                emptyMap()
            }
        }
    }
}