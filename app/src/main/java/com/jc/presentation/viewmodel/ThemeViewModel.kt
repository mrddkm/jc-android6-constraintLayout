package com.jc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jc.domain.usecase.theme.GetThemeUseCase
import com.jc.domain.usecase.theme.HasUserMadeThemeChoiceUseCase
import com.jc.domain.usecase.theme.SaveThemeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val getThemeUseCase: GetThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
    private val hasUserMadeThemeChoiceUseCase: HasUserMadeThemeChoiceUseCase
) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow<Boolean?>(null)
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    init {
        loadInitialTheme()
    }

    private fun loadInitialTheme() {
        viewModelScope.launch {
            val userHasChosen = hasUserMadeThemeChoiceUseCase().first()
            if (userHasChosen) {
                getThemeUseCase().collectLatest { themePreference ->
                    _isDarkTheme.value = themePreference
                }
            } else {
                _isDarkTheme.value = null
            }
        }
    }

    fun setTheme(isDark: Boolean) {
        try {
            viewModelScope.launch {
                saveThemeUseCase(isDark)
                _isDarkTheme.value = isDark
            }
        } catch (e: Exception) {
            println("ThemeViewModel: Error saving theme preference: ${e.message}")
        }
    }
}