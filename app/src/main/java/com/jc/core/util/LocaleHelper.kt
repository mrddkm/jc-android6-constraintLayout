package com.jc.core.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import java.util.Locale
import androidx.core.content.edit

object LocaleHelper {

    private const val SELECTED_LANGUAGE = "selected_language"

    fun setLocale(context: Context, languageCode: String): Context {
        Log.d("LocaleHelper", "Setting locale to: $languageCode")
        persist(context, languageCode)
        return updateResources(context, languageCode)
    }

    fun getLanguage(context: Context): String {
        val prefs = getPreferences(context)
        return prefs.getString(SELECTED_LANGUAGE, Constants.DEFAULT_LANGUAGE_CODE)
            ?: Constants.DEFAULT_LANGUAGE_CODE
    }

    private fun persist(context: Context, languageCode: String) {
        val prefs = getPreferences(context)
        prefs.edit { putString(SELECTED_LANGUAGE, languageCode) }
        Log.d("LocaleHelper", "Persisted language: $languageCode")
    }

    private fun updateResources(context: Context, languageCode: String): Context {
        @Suppress("DEPRECATION") val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            val localeList = android.os.LocaleList(locale)
            android.os.LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            context.createConfigurationContext(configuration)
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
            @Suppress("DEPRECATION")
            context.createConfigurationContext(configuration)
        }
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
    }

    fun getCurrentLanguage(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)?.language ?: Constants.DEFAULT_LANGUAGE_CODE
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale?.language ?: Constants.DEFAULT_LANGUAGE_CODE
        }
    }

    fun applyLanguageToContext(context: Context): Context {
        val savedLanguage = getLanguage(context)
        Log.d("LocaleHelper", "Applying saved language: $savedLanguage")
        return updateResources(context, savedLanguage)
    }
}