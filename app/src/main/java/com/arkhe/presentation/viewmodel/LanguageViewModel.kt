package com.arkhe.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.constraintlayout.R
import com.arkhe.core.utils.ConsLang
import com.arkhe.core.utils.Constants
import com.arkhe.core.utils.LanguageManager
import com.arkhe.data.repository.language.LanguageRepository
import com.arkhe.model.language.Language
import com.arkhe.model.language.LanguageState
import com.arkhe.model.language.Languages
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
                /*Company*/
                ConsLang.COMPANY to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.company,
                    languageCode
                ),
                ConsLang.COMPANY_NAME to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.company_name,
                    languageCode
                ),
                ConsLang.COPYRIGHT to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.copyright,
                    languageCode
                ),
                /*App*/
                ConsLang.APP_NAME to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.app_name,
                    languageCode
                ),
                ConsLang.HELLO_WORLD to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.hello_world,
                    languageCode
                ),
                ConsLang.TEMPLATE_DESCRIPTION to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.template_description,
                    languageCode
                ),
                ConsLang.FRAMEWORK to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.framework,
                    languageCode
                ),
                ConsLang.FRAMEWORK_NAME to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.framework_name,
                    languageCode
                ),
                /*BottomSheet*/
                ConsLang.SETTINGS_TITLE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.settings_title,
                    languageCode
                ),
                ConsLang.SETTINGS_PROFILE_TITLE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.settings_profile_title,
                    languageCode
                ),
                ConsLang.SETTINGS_SECTION_PROFILE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.settings_section_profile,
                    languageCode
                ),
                ConsLang.SETTINGS_SECTION_LANGUAGE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.settings_section_language,
                    languageCode
                ),
                ConsLang.SELECT_LANGUAGE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.select_language,
                    languageCode
                ),
                ConsLang.CURRENT_LANGUAGE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.current_language,
                    languageCode
                ),
                /*Common*/
                ConsLang.SUBMIT_BUTTON to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.submit_button,
                    languageCode
                ),
                ConsLang.CHECK_BUTTON to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.check_button,
                    languageCode
                ),
                ConsLang.USERID to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.user_id,
                    languageCode
                ),
                ConsLang.USERID_PLACEHOLDER to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.user_id_placeholder,
                    languageCode
                ),
                ConsLang.PASSWORD to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.password,
                    languageCode
                ),
                ConsLang.PASSWORD_PLACEHOLDER to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.password_placeholder,
                    languageCode
                ),
                /*Activation*/
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
                /*SignIn*/
                ConsLang.SIGN_IN_TITLE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.sign_in_title,
                    languageCode
                ),
                ConsLang.SIGN_IN_SUBTITLE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.sign_in_subtitle,
                    languageCode
                ),
                /*Main*/
                ConsLang.APP_CLIENT to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.app_client,
                    languageCode
                ),
                ConsLang.APP_REGION to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.app_region,
                    languageCode
                ),
                ConsLang.APP_PRODUCT_NAME to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.app_product_name,
                    languageCode
                ),
                ConsLang.VEHICLE_PLATE to LanguageManager.getLocalizedString(
                    getApplication(),
                    R.string.vehicle_plate,
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