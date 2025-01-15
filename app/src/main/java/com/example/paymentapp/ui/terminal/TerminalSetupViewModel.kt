package com.example.paymentapp.ui.terminal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.paymentapp.data.terminal.Terminal
import com.example.paymentapp.data.terminal.TerminalRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


/**
 * UI state for the Terminal Setup Route
 *
 * This is derived from [TerminalSetupViewModelState], but split into subclasses to
 * more precisely represent the state available to render the UI
 */
sealed interface TerminalSetupUiState {

    val isLoading: Boolean
    val availableTerminalTypes: List<Terminal>

    data class TerminalTypes(
        override val isLoading: Boolean,
        override val availableTerminalTypes: List<Terminal>
    ) : TerminalSetupUiState

    data class InitiateBluetoothScan(
        override val isLoading: Boolean,
        override val availableTerminalTypes: List<Terminal>,
    ) : TerminalSetupUiState

}


/**
 * Internal representation of the Terminal Setup Screen UI state in raw form
 */
private data class TerminalSetupViewModelState(
    val isLoading: Boolean = false,
    val terminalTypes: List<Terminal>,
    val isConnected: Boolean = false,
    val isBluetoothScanStart: Boolean = false,
    val isBluetoothScanInProgress: Boolean = false,
    val isBluetoothScanCompleted: Boolean = false,
    val bluetoothDevices: List<String> = emptyList(),
    val selectedTerminalType: Terminal? = null
) {
    /**
     * Convert to appropriate [TerminalSetupUiState]
     */
    fun toUiState(): TerminalSetupUiState {
        return if (isBluetoothScanStart) {
            TerminalSetupUiState.InitiateBluetoothScan(
                isLoading = false,
                availableTerminalTypes = terminalTypes
            )
        } else {
            TerminalSetupUiState.TerminalTypes(
                false,
                terminalTypes
            )
        }
    }
}

/**
 * ViewModel that handles the business logic for the Terminal Setup screen
 */
class TerminalSetupViewModel(private val terminalRepo: TerminalRepo) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        TerminalSetupViewModelState(
            terminalTypes = emptyList()
        )
    )

    // UI state exposed to the UI
    val uiState = viewModelState.map(TerminalSetupViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        viewModelState.update {
            it.copy(terminalTypes = terminalRepo.getAvailableTerminals())
        }
    }

    fun startBluetoothScan() {
        viewModelState.update {
            it.copy(
                isBluetoothScanStart = true,
                isLoading = false
            )
        }
    }

    companion object {
        fun provideFactory(terminalRepo: TerminalRepo): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TerminalSetupViewModel(terminalRepo) as T
                }
            }
    }

}