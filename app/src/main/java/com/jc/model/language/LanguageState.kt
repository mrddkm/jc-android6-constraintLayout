package com.jc.model.language

data class LanguageState(
    val currentLanguage: Language = Languages.ENGLISH,
    val isChangingLanguage: Boolean = false,
    val localizedStrings: Map<String, String> = emptyMap()
)