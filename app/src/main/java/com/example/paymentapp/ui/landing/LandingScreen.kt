package com.example.paymentapp.ui.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paymentapp.R
import com.example.paymentapp.ui.components.CommonButton
import com.example.paymentapp.ui.theme.PaymentAppTheme


@Composable
fun LandingScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    onClickTerminalSetup: () -> Unit,
    onClickTransaction: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val buttonSpecs = Modifier.fillMaxWidth().height(50.dp)
        CommonButton (buttonSpecs = buttonSpecs,labelResource = R.string.section_terminal_setup, onClick = onClickTerminalSetup)
        Spacer(Modifier.size(30.dp))
        CommonButton(buttonSpecs = buttonSpecs, labelResource = R.string.section_perform_transaction, onClick = onClickTransaction)
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    PaymentAppTheme {
        LandingScreen(
            false,
            openDrawer = {},
            onClickTransaction = {},
            onClickTerminalSetup = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}