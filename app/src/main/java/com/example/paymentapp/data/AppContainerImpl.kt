package com.example.paymentapp.data

import android.content.Context

/**
 * Dependency Injection container at the application level
 */
interface AppContainer {
    // TODO: Define TerminalRepository for getting data about a terminal
    val terminalRepository: Unit
    val appContext: Context
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val terminalRepository: Unit by lazy {
    }
    override val appContext = applicationContext
}