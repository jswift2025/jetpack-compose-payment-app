package com.example.paymentapp.ui.terminal

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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

    TerminalSetupScreen(
        uiState,
        isExpandedScreen = isExpandedScreen,
        openDrawer = openDrawer,
        snackbarHostState = snackbarHostState
    )
}