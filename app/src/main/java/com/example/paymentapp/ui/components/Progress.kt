package com.example.paymentapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paymentapp.R
import com.example.paymentapp.ui.theme.PaymentAppTheme


/**
 * Display a labelled indeterminate progress indicator in a column with the text above the indicator.
 * @param modifier Optional specifications for appearance of the column. A default padding of 10.dp is
 * applied.
 */
@Composable
fun LabelledIndeterminateProgressIndicator(
    @StringRes labelResource: Int,
    modifier: Modifier = Modifier.padding(10.dp)) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(labelResource), style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                letterSpacing = 0.5.sp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(width = 100.dp, height = 100.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview
@Composable
fun LabelledIndeterminateProgressIndicatorPreview() {
    PaymentAppTheme {
        LabelledIndeterminateProgressIndicator(R.string.label_bluetooth_search)
    }
}