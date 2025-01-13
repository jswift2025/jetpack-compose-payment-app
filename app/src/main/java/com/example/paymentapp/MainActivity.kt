package com.example.paymentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.paymentapp.ui.PaymentApp
import com.example.paymentapp.ui.theme.PaymentAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appContainer = (application as PaymentApplication).container
        setContent {
            PaymentAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
                    PaymentApp(appContainer, widthSizeClass, Modifier.padding(innerPadding))
                }
            }
        }
    }
}