package com.example.paymentapp

import android.app.Application
import com.example.paymentapp.data.AppContainer
import com.example.paymentapp.data.AppContainerImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PaymentApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }

}