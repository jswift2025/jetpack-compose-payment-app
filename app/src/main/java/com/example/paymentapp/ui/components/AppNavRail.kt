package com.example.paymentapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paymentapp.R
import com.example.paymentapp.ui.PaymentDestinations
import com.example.paymentapp.ui.theme.PaymentAppTheme

@Composable
fun AppNavRail(
    currentRoute: String,
    navigateToLanding: () -> Unit,
    navigateToTerminalSetup: () -> Unit,
    navigateToTransaction: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        header = {
            Icon(
                painterResource(R.drawable.ic_jetnews_logo),
                null,
                Modifier.padding(vertical = 12.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        NavigationRailItem(
            selected = currentRoute == PaymentDestinations.LANDING_ROUTE,
            onClick = navigateToLanding,
            icon = { Icon(Icons.Filled.Home, stringResource(R.string.section_home)) },
            label = { Text(stringResource(R.string.section_home)) },
            alwaysShowLabel = false
        )
        NavigationRailItem(
            selected = currentRoute == PaymentDestinations.TERMINAL_SETUP_ROUTE,
            onClick = navigateToTerminalSetup,
            icon = { Icon(Icons.Filled.Add, stringResource(R.string.section_terminal_setup)) },
            label = { Text(stringResource(R.string.section_terminal_setup)) },
            alwaysShowLabel = false
        )
        NavigationRailItem(
            selected = currentRoute == PaymentDestinations.TRANSACTION_ROUTE,
            onClick = navigateToTransaction,
            icon = {
                Icon(
                    Icons.Filled.ShoppingCart,
                    stringResource(R.string.section_perform_transaction)
                )
            },
            label = { Text(stringResource(R.string.section_perform_transaction)) },
            alwaysShowLabel = false
        )
        Spacer(Modifier.weight(1f))

    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppNavRail() {
    PaymentAppTheme {
        AppNavRail(
            currentRoute = PaymentDestinations.LANDING_ROUTE,
            navigateToLanding = {},
            navigateToTerminalSetup = {},
            navigateToTransaction = {}
        )
    }
}