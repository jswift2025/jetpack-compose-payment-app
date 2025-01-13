package com.example.paymentapp.ui.landing

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.paymentapp.ui.PaymentNavigationActions

/**
 * Displays the Landing route. Responsibility is to manage setting up logic for landing screen.
 */

private const val TAG = "Landing"

@Composable
fun LandingRoute(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val navigationActions = remember(navController) {
        PaymentNavigationActions(navController)
    }
    // TODO: Update buttons to navigate to the appropriate destination in the NavGraph
    LandingRoute(
        isExpandedScreen = isExpandedScreen,
        onClickTerminalSetup = {
            navigationActions.navigateToTerminalSetup.invoke()
        },
        onClickTransaction = { Log.d(TAG, "Transaction clicked") },
        openDrawer = openDrawer,
        snackbarHostState = snackbarHostState
    )
}

/**
 * Displays the Landing route.
 *
 * This composable is not coupled to any specific state management.
 * @param isExpandedScreen (state) whether the screen is expanded
 * @param onClickTerminalSetup (event) navigate to terminal setup screen
 * @param onClickTransaction (event) navigate to transaction screen
 * @param onClickTerminalSettings (event) navigate to terminal settings screen
 * @param onClickTerminalUpdate (event) navigate to terminal update screen
 * @param openDrawer (event) request opening the app drawer
 */
@Composable
fun LandingRoute(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    onClickTerminalSetup: () -> Unit,
    onClickTransaction: () -> Unit,
    snackbarHostState: SnackbarHostState) {
    LandingScreen(
        isExpandedScreen = isExpandedScreen,
        openDrawer = openDrawer,
        onClickTerminalSetup = onClickTerminalSetup,
        onClickTransaction = onClickTransaction,
        snackbarHostState = snackbarHostState
    )

}