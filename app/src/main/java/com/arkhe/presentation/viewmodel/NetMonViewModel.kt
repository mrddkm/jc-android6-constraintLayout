package com.arkhe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.domain.model.NetMonState
import com.arkhe.domain.usecase.netmon.GetNetMonStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NetMonViewModel(
    private val getNetMonStateUseCase: GetNetMonStateUseCase
) : ViewModel() {

    private val _netMonState =
        MutableStateFlow<NetMonState>(NetMonState.NotConnectedToInternet)
    val netMonState: StateFlow<NetMonState> = _netMonState.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    init {
        observeConnectionState()
    }

    private fun observeConnectionState() {
        viewModelScope.launch {
            getNetMonStateUseCase().collect { state ->
                _netMonState.value = state
            }
        }
    }

    fun showNetMonDialog() {
        _showDialog.value = true
    }

    fun hideNetMonDialog() {
        _showDialog.value = false
    }
}