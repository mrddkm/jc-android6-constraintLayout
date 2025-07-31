package com.arkhe.model.language

import android.os.Parcelable
import com.arkhe.core.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    val code: String,
    val name: String,
    val nativeName: String
) : Parcelable

object Languages {
    val ENGLISH = Language(
        Constants.LANGUAGE_CODE_ENGLISH,
        Constants.LANGUAGE_DISPLAY_NAME_ENGLISH,
        Constants.LANGUAGE_NATIVE_NAME_ENGLISH
    )
    val INDONESIAN = Language(
        Constants.LANGUAGE_CODE_INDONESIAN,
        Constants.LANGUAGE_DISPLAY_NAME_INDONESIAN,
        Constants.LANGUAGE_NATIVE_NAME_INDONESIAN
    )

    val availableLanguages = listOf(ENGLISH, INDONESIAN)

    fun getLanguageByCode(code: String): Language {
        return availableLanguages.find { it.code == code } ?: ENGLISH
    }
}
