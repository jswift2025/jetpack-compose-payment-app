package com.example.paymentapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.paymentapp.connectivity.getRequiredBluetoothPermissions
import com.example.paymentapp.data.AppContainer
import com.example.paymentapp.ui.components.AppNavRail
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PaymentApp(
    appContainer: AppContainer,
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        PaymentNavigationActions(navController)
    }

    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: PaymentDestinations.LANDING_ROUTE

    val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    // Check for permissions
    val multiplePermissionsState = rememberMultiplePermissionsState(
        getRequiredBluetoothPermissions()
    )
    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(drawerState = sizeAwareDrawerState,
                currentRoute = currentRoute,
                navigateToHome = navigationActions.navigateToLanding,
                navigateToTerminalSetup = navigationActions.navigateToTerminalSetup,
                navigateToPerformTransaction = navigationActions.navigateToTransaction,
                closeDrawer = {
                    coroutineScope.launch {
                        sizeAwareDrawerState.close()
                    }
                })
        }, drawerState = sizeAwareDrawerState,
        // Only enable the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen
    ) {
        Row {
            if (isExpandedScreen) {
                AppNavRail(
                    currentRoute = currentRoute,
                    navigateToLanding = navigationActions.navigateToLanding,
                    navigateToTerminalSetup = navigationActions.navigateToTerminalSetup,
                    navigateToTransaction = navigationActions.navigateToTransaction
                )
            }
        }
        if (multiplePermissionsState.permissions.isEmpty() || multiplePermissionsState.allPermissionsGranted) {
            PaymentNavGraph(appContainer = appContainer,
                isExpandedScreen = isExpandedScreen,
                navController = navController,
                openDrawer = {
                    coroutineScope.launch {
                        sizeAwareDrawerState.open()
                    }
                })
        } else {
            SideEffect {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        }
    }
}

/**
 * Determine the drawer state to pass to the modal drawer
 */
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}