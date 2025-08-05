package com.arkhe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsProfileViewModel(): ViewModel() {
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    fun showSettingsProfileBottomSheet() {
        _showBottomSheet.value = true
    }

    fun hideSettingsProfileBottomSheet() {
        _showBottomSheet.value = false
    }
}