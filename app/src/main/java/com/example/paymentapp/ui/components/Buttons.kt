package com.example.paymentapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

/**
 * Style the color of the button and text for [CommonButton]
 */
sealed class CustomButtonStyle {

    data class Default(val buttonColors: ButtonColors?) :
        CustomButtonStyle()


    data class Pressed(val buttonColors: ButtonColors?) :
        CustomButtonStyle()
}

@Composable
fun CommonButton(
    buttonSpecs: Modifier = Modifier,
    @StringRes labelResource: Int,
    onClick: () -> Unit = {},
    buttonStyleDefault: CustomButtonStyle.Default? = null,
    buttonStylePressed: CustomButtonStyle.Pressed? = null,
    enabled: Boolean = true) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonColors = if (isPressed) {
        buttonStylePressed?.buttonColors ?: ButtonDefaults.buttonColors()
    } else {
        buttonStyleDefault?.buttonColors ?: ButtonDefaults.buttonColors()
    }

    Button(
        modifier = buttonSpecs,
        shape = ButtonDefaults.elevatedShape,
        colors = buttonColors,
        onClick = onClick,
        interactionSource = interactionSource,
        enabled = enabled
    ) {
        Text(text = stringResource(labelResource))
    }
}