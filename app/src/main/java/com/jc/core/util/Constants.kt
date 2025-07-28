@file:Suppress("DEPRECATION")

package com.jc.core.util

import java.util.Locale

object Constants {

    val localeUS = Locale("en", "US")
    val localeID = Locale("id", "ID")

    // Shared Preferences keys
    const val PREFS_NAME_LANGUAGE = "language_settings"
    const val KEY_SELECTED_LANGUAGE = "selected_language_code"

    // Language codes & display names
    val LANGUAGE_DISPLAY_NAME_ENGLISH: String = localeUS.displayLanguage
    val LANGUAGE_DISPLAY_NAME_INDONESIAN: String = localeID.displayLanguage
    val LANGUAGE_CODE_ENGLISH: String = localeUS.language
    val LANGUAGE_CODE_INDONESIAN: String = localeID.language
    val DEFAULT_LANGUAGE_CODE = LANGUAGE_CODE_ENGLISH
}