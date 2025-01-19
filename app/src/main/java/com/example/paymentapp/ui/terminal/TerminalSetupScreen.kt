package com.example.paymentapp.ui.terminal

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.paymentapp.R
import com.example.paymentapp.connectivity.BluetoothScanObserver
import com.example.paymentapp.data.terminal.Terminal
import com.example.paymentapp.ui.components.CommonButton
import com.example.paymentapp.ui.components.CustomButtonStyle
import com.example.paymentapp.ui.components.LabelledIndeterminateProgressIndicator
import com.example.paymentapp.ui.theme.PaymentAppTheme

private const val TAG = "TerminalSetupScreen"

@Composable
fun TerminalSetupScreen(
    uiState: TerminalSetupUiState,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    onScanClick: () -> Unit,
    snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier) {
    val baseModifier = Modifier.padding(start = 10.dp, end = 10.dp)
    val context = LocalContext.current

    var scanClickEnabled by remember { mutableStateOf(true) }

    if (uiState is TerminalSetupUiState.BluetoothScanInProgress) {
        scanClickEnabled = false
    } else {
        scanClickEnabled = true
    }

    Column(
        modifier = baseModifier.padding(top = 80.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        TerminalActionsButtonPanel(
            modifier = modifier,
            onScanClick = onScanClick,
            onDisconnectClick = {},
            onConnectClick = {},
            onInfoClick = {},
            scanClickEnabled = scanClickEnabled
        )
        TerminalDetailsPanel(uiState, modifier)
        when (uiState) {
            is TerminalSetupUiState.InitiateBluetoothScan -> {
                BluetoothScanObserver(
                    lifecycleOwner = LocalLifecycleOwner.current,
                    bluetoothObserver = uiState.bluetoothReceiver,
                    registerObserver = true
                )
            }

            is TerminalSetupUiState.TerminalTypes -> {
                // no-op This is displayed by default
            }

            is TerminalSetupUiState.BluetoothScanComplete -> {
                BluetoothSearchResultsList(uiState.bluetoothDevices)
            }

            is TerminalSetupUiState.BluetoothScanInProgress -> {
                LabelledIndeterminateProgressIndicator(R.string.label_bluetooth_search)
            }
        }
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
    onInfoClick: () -> Unit,
    scanClickEnabled: Boolean = true) {

    val buttonPressedState = CustomButtonStyle.Pressed(
        ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.LightGray
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CommonButton(
                buttonSpecs = Modifier.weight(0.5f),
                labelResource = R.string.label_scan,
                onClick = onScanClick,
                buttonStylePressed = buttonPressedState,
                enabled = scanClickEnabled
            )
            CommonButton(
                buttonSpecs = Modifier.weight(0.5f),
                labelResource = R.string.label_connect,
                onClick = onConnectClick,
                buttonStylePressed = buttonPressedState,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CommonButton(
                buttonSpecs = Modifier.weight(0.5f),
                labelResource = R.string.label_disconnect,
                onClick = onDisconnectClick,
                buttonStylePressed = buttonPressedState,
            )
            CommonButton(
                buttonSpecs = Modifier.weight(0.5f),
                labelResource = R.string.label_info,
                onClick = onInfoClick,
                buttonStylePressed = buttonPressedState,
            )
        }
    }
}

/**
 * Displays terminal type dropdown, Selected Terminal and Connection Status
 */
@Composable
fun TerminalDetailsPanel(
    uiState: TerminalSetupUiState,
    modifier: Modifier = Modifier) {
    val availableTerminals = remember(uiState) {
        uiState.availableTerminalTypes
    }
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            TerminalTypesDropdown(availableTerminals, modifier = Modifier.weight(0.6f))
            Column(modifier = Modifier.weight(0.4f)) {
                Text(text = stringResource(R.string.label_selected_terminal))
                Text(text = stringResource(R.string.common_placeholder)) // TODO: Display the terminal selected in the dropdown
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text(text = stringResource(R.string.label_connection_status), fontWeight = FontWeight.Bold)
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
fun TerminalTypesDropdown(availableTerminals: List<Terminal>, modifier: Modifier) {
    // Store the expanded state of the Text Field
    var expanded by remember { mutableStateOf(false) }

    // Store the selected terminal
    var selectedTerminal by remember { mutableStateOf("") }

    var inputFieldSize by remember { mutableStateOf(Size.Zero) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // User clicks into the Textfield, displaying the dropdown
    if (isPressed) {
        expanded = true
    }

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
            readOnly = true,
            interactionSource = interactionSource,
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
    }

    // Drop-down menu with list of terminals. When clicked, set the Text Field text as the terminal selected
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.width(with(LocalDensity.current) {
            inputFieldSize.width.toDp()
        })
    ) {
        availableTerminals.forEach { terminal ->
            DropdownMenuItem(onClick = {
                selectedTerminal = terminal.displayName
                expanded = false
            }, text = {
                Text(text = terminal.displayName)
            })
        }
    }

}

@Composable
fun BluetoothSearchResultsList(
    devices: List<BluetoothDevice> = emptyList(),
    modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.label_bluetooth_search_results),
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp)
    )
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(devices.size, key = { devices[it].address }) {
            BluetoothSearchResultListItem(devices[it])
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothSearchResultListItem(device: BluetoothDevice, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 4.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = device.name, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = device.address,
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
        )
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
        TerminalDetailsPanel(
            uiState = TerminalSetupUiState.TerminalTypes(false, emptyList()),
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun PreviewTerminalSetupScreen() {
    PaymentAppTheme {
        TerminalSetupScreen(
            TerminalSetupUiState.TerminalTypes(false, emptyList()),
            isExpandedScreen = false,
            openDrawer = {},
            onScanClick = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}