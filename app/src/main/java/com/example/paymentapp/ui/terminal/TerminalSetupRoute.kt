package com.example.paymentapp.ui.terminal

import android.bluetooth.BluetoothManager
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.paymentapp.connectivity.BluetoothReceiver

/**
 * Displays the Terminal Setup route. Responsibility is to manage setting up navigation to the
 * terminal setup screen.
 */

private const val TAG = "TerminalSetup"

/**
 * Displays the Terminal Setup route.
 *
 * This composable is not coupled to any specific state management.
 * @param isExpandedScreen (state) whether the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@Composable
fun TerminalSetupRoute(
    viewModel: TerminalSetupViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val onScanClick: () -> Unit = {
        val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter = bluetoothManager.adapter
        if (bluetoothAdapter?.isEnabled == true) {
            val bluetoothReceiver = BluetoothReceiver(
                onDiscoveryStart = {
                    viewModel.updateBluetoothScanProgress()
                },
                onDiscoveryComplete = { discoveredDevices ->
                    viewModel.loadBluetoothDevices(discoveredDevices)
                }
            )
            viewModel.startBluetoothScan(bluetoothReceiver)
        }
    }

    TerminalSetupScreen(
        uiState,
        isExpandedScreen = isExpandedScreen,
        openDrawer = openDrawer,
        onScanClick = onScanClick,
        snackbarHostState = snackbarHostState
    )
}