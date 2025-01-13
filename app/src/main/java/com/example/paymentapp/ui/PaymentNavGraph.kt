package com.example.paymentapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paymentapp.data.AppContainer
import com.example.paymentapp.data.terminal.impl.TerminalRepoImpl
import com.example.paymentapp.ui.landing.LandingRoute
import com.example.paymentapp.ui.terminal.TerminalSetupRoute
import com.example.paymentapp.ui.terminal.TerminalSetupViewModel


@Composable
fun PaymentNavGraph(
    appContainer: AppContainer,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = PaymentDestinations.LANDING_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = PaymentDestinations.LANDING_ROUTE
        ) {
            LandingRoute(
                isExpandedScreen = isExpandedScreen,
                navController = navController,
                openDrawer = openDrawer
            )
        }
        composable(
            route = PaymentDestinations.TERMINAL_SETUP_ROUTE
        ) {
            val viewModel: TerminalSetupViewModel =
                viewModel(factory = TerminalSetupViewModel.provideFactory(TerminalRepoImpl()))
            TerminalSetupRoute(
                viewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(
            route = PaymentDestinations.TRANSACTION_ROUTE
        ) {

        }
    }


}