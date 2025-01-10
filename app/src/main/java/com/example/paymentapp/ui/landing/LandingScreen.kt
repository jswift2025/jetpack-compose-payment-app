package com.example.paymentapp.ui.landing

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
        verticalArrangement = Arrangement.Center
    ) {
        CommonButton (labelResource = R.string.section_terminal_setup, onClick = onClickTerminalSetup)
        CommonButton(labelResource = R.string.section_perform_transaction, onClick = onClickTransaction)
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