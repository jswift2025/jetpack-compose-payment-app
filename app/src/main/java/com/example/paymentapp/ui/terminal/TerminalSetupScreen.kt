package com.example.paymentapp.ui.terminal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.paymentapp.R
import com.example.paymentapp.ui.components.CommonButton
import com.example.paymentapp.ui.theme.PaymentAppTheme

@Composable
fun TerminalSetupScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier) {
    val baseModifier = Modifier.padding(start = 10.dp, end = 10.dp)
    Column(
        modifier = baseModifier.padding(top = 80.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        TerminalActionsButtonPanel(
            modifier = modifier,
            onScanClick = {},
            onDisconnectClick = {},
            onConnectClick = {},
            onInfoClick = {}
        )
        TerminalDetailsPanel(modifier)
    }
}

/**
 * Displays the Scan, Connect, Disconnect and Info buttons in a 2 X 2 grid.
 */
@Composable
fun TerminalActionsButtonPanel(
    modifier: Modifier = Modifier,
    onScanClick: () -> Unit,
    onConnectClick: () -> Unit,
    onDisconnectClick: () -> Unit,
    onInfoClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CommonButton(
                modifier = Modifier.weight(0.5f),
                labelResource = R.string.label_scan,
                onClick = onScanClick
            )
            CommonButton(
                modifier = Modifier.weight(0.5f),
                labelResource = R.string.label_connect,
                onClick = onConnectClick
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CommonButton(
                modifier = Modifier.weight(0.5f),
                labelResource = R.string.label_disconnect,
                onClick = onDisconnectClick
            )
            CommonButton(
                modifier = Modifier.weight(0.5f),
                labelResource = R.string.label_info,
                onClick = onInfoClick
            )
        }
    }
}

/**
 * Displays terminal type dropdown, Selected Terminal and Connection Status
 */
@Composable
fun TerminalDetailsPanel(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            TerminalTypesDropdown(modifier = Modifier.weight(0.6f))
            Column(modifier = Modifier.weight(0.4f)) {
                Text(text = stringResource(R.string.label_selected_terminal))
                Text(text = stringResource(R.string.common_placeholder)) // TODO: Display the terminal selected in the dropdown
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text(text = stringResource(R.string.label_connection_status))
        CircularShape(modifier = Modifier, enclosingBoxSideSize = 50.dp)
    }
}

/**
 * Common rounded shape
 * @param enclosingBoxSize (Dp) Represents length of the edges of the box from which the circle
 * is created. Example: 10.0dp is a box of area 100
 */
@Composable
fun CircularShape(modifier: Modifier, enclosingBoxSideSize: Dp) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Box(
            modifier = Modifier
                .size(enclosingBoxSideSize)
                .clip(CircleShape)
                .background(Color.Red)
        )
    }
}

/**
 * Display list of terminal types.
 */
@Composable
fun TerminalTypesDropdown(modifier: Modifier) {
    // Store the expanded state of the Text Field
    var expanded by remember { mutableStateOf(false) }

    val terminalTypes = listOf(
        "350X",
        "457C",
        "45-BT",
        "Moby-3000",
        "Moby-8500",
        "Moby-5500",
        "G5X",
        "C2X",
        "Magtek-aDynamo"
    )

    // Store the selected terminal
    var selectedTerminal by remember { mutableStateOf("") }

    var inputFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = modifier) {
        // Create an Outlined Text Field with icon and not expanded
        OutlinedTextField(
            value = selectedTerminal,
            onValueChange = { selectedTerminal = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to the Dropdown the same width
                    inputFieldSize = coordinates.size.toSize()
                },
            label = { Text(stringResource(R.string.label_selected_terminal)) },
            trailingIcon = {
                Icon(icon, "", Modifier.clickable {
                    expanded = !expanded
                })
            }
        )

        // Drop-down menu with list of terminals. When clicked, set the Text Field text as the terminal selected
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) {
                inputFieldSize.width.toDp()
            })
        ) {
            terminalTypes.forEach { terminal ->
                DropdownMenuItem(onClick = {
                    selectedTerminal = terminal
                    expanded = false
                }, text = {
                    Text(text = terminal)
                })
            }
        }

    }
}

@Preview
@Composable
fun PreviewTerminalActionsButtonPanel() {
    PaymentAppTheme {
        TerminalActionsButtonPanel(
            onScanClick = {},
            onDisconnectClick = {},
            onConnectClick = {},
            onInfoClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewTerminalDetailsPanel() {
    PaymentAppTheme {
        TerminalDetailsPanel()
    }
}

@Preview
@Composable
fun PreviewTerminalSetupScreen() {
    PaymentAppTheme {
        TerminalSetupScreen(
            isExpandedScreen = false,
            openDrawer = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}