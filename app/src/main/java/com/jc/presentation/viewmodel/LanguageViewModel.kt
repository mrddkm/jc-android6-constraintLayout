package com.jc.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jc.constraintlayout.R
import com.jc.core.utils.ConsLang
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

    private val _languageState = MutableStateFlow(LanguageState())
    val languageState: StateFlow<LanguageState> = _languageState.asStateFlow()

    init {
        observeLanguageChanges()
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
        return _languageState.value.localizedStrings[key] ?: ""
    }

    fun selectLanguage(language: Language) {
        viewModelScope.launch {
            val currentLanguage = _languageState.value.currentLanguage

            if (currentLanguage.code != language.code) {
                _languageState.value = _languageState.value.copy(isChangingLanguage = true)

                languageRepository.setLanguage(language.code)

                delay(800) // 800ms loading

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
        return mapOf(
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
    }
}