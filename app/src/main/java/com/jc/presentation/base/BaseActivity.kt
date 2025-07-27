package com.jc.presentation.base

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.activity.ComponentActivity
import com.jc.core.util.LocaleHelper

abstract class BaseActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        Log.d("BaseActivity", "attachBaseContext called for ${this::class.simpleName}")
        val context = LocaleHelper.applyLanguageToContext(newBase)
        super.attachBaseContext(context)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("BaseActivity", "onConfigurationChanged called")
        // Force recreate to apply new locale
        recreate()
    }

    fun changeLanguage(languageCode: String) {
        Log.d("BaseActivity", "changeLanguage called with: $languageCode")
        LocaleHelper.setLocale(this, languageCode)
        recreate()
    }
}