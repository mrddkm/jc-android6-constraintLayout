package com.arkhe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.domain.model.ConnectionState
import com.arkhe.domain.usecase.network.GetConnectionStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConnectionViewModel(
    private val getConnectionStateUseCase: GetConnectionStateUseCase
) : ViewModel() {

    private val _connectionState =
        MutableStateFlow<ConnectionState>(ConnectionState.NotConnectedToInternet)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    init {
        observeConnectionState()
    }

    private fun observeConnectionState() {
        viewModelScope.launch {
            getConnectionStateUseCase().collect { state ->
                _connectionState.value = state
            }
        }
    }

    fun showConnectionDialog() {
        _showDialog.value = true
    }

    fun hideConnectionDialog() {
        _showDialog.value = false
    }
}