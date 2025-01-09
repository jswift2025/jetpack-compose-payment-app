package com.example.paymentapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.paymentapp.R


@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToTerminalSetup: () -> Unit,
    navigateToPerformTransaction: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationDrawerItem(
        label = {
            Text(stringResource(R.string.section_home))
        },
        icon = {
            Icon(Icons.Filled.Home, null)
        },
        selected = true,
        onClick = {
            navigateToHome(); closeDrawer()
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
    NavigationDrawerItem(
        label = {
            Text(stringResource(R.string.section_terminal_setup))
        },
        icon = {
            Icon(Icons.Filled.List, null)
        },
        selected = false,
        onClick = {
            navigateToTerminalSetup(); closeDrawer()
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
    NavigationDrawerItem(
        label = {
            Text(stringResource(R.string.section_perform_transaction))
        },
        icon = {
            Icon(Icons.Filled.List, null)
        },
        selected = false,
        onClick = {
            navigateToPerformTransaction(); closeDrawer()
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}