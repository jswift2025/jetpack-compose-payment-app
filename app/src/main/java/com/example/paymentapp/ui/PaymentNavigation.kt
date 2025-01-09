package com.example.paymentapp.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object PaymentDestinations {
    const val LANDING_ROUTE = "landing"
    const val TERMINAL_SETUP_ROUTE = "terminal_setup"
    const val TRANSACTION_ROUTE = "transaction"
}

/**
 * Models the navigation actions in the app.
 */
class PaymentNavigationActions(navController: NavHostController) {
    val navigateToLanding: () -> Unit = {
        navController.navigate(PaymentDestinations.LANDING_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true

        }
    }
    val navigateToTerminalSetup: () -> Unit = {
        navController.navigate(PaymentDestinations.TERMINAL_SETUP_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true

        }
    }
    val navigateToTransaction: () -> Unit = {
        navController.navigate(PaymentDestinations.TRANSACTION_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true

        }
    }
}