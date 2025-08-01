package com.arkhe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.domain.model.ThemeMode
import com.arkhe.domain.usecase.theme.GetCurrentThemeUseCase
import com.arkhe.domain.usecase.theme.SetThemeUseCase
import com.arkhe.presentation.state.ThemeUIType
import com.arkhe.presentation.state.ThemeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ThemeViewModel(
    getCurrentThemeUseCase: GetCurrentThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase
) : ViewModel() {

    private val _selectedUIType = MutableStateFlow(ThemeUIType.CYCLING_BUTTON)
    private val selectedUIType: StateFlow<ThemeUIType> = _selectedUIType.asStateFlow()

    val uiState: StateFlow<ThemeUiState> = combine(
        getCurrentThemeUseCase(),
        selectedUIType
    ) { currentTheme, uiType ->
        ThemeUiState(
            currentTheme = currentTheme,
            selectedUIType = uiType
        )
    }.stateInViewModelScope(ThemeUiState())

    fun setTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            setThemeUseCase(themeMode)
        }
    }

    fun cycleTheme() {
        val currentTheme = uiState.value.currentTheme
        val nextTheme = when (currentTheme) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.AUTOMATIC
            ThemeMode.AUTOMATIC -> ThemeMode.LIGHT
        }
        setTheme(nextTheme)
    }
}

/*Extension function from StateFlow*/
private fun <T> kotlinx.coroutines.flow.Flow<T>.stateInViewModelScope(
    initialValue: T
): StateFlow<T> = MutableStateFlow(initialValue).apply {
    kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
        this@stateInViewModelScope.collect { value = it }
    }
}.asStateFlow()