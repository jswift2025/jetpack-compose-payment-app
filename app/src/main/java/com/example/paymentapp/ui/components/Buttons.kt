package com.example.paymentapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CommonButton(
    buttonSpecs: Modifier = Modifier,
    @StringRes labelResource: Int,
    onClick: () -> Unit = {},
    enabled:Boolean = true) {
    Button(
        modifier = buttonSpecs,
        shape = ButtonDefaults.elevatedShape,
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = stringResource(labelResource))
    }
}