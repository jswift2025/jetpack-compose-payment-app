package com.example.paymentapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    @StringRes labelResource: Int,
    onClick: () -> Unit) {
    Button(
        modifier = modifier,
        shape = ButtonDefaults.elevatedShape,
        onClick = onClick
    ) {
        Text(text = stringResource(labelResource))
    }
}