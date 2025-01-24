package com.example.paymentapp.data

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

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
class AppContainerImpl @Inject constructor(@ApplicationContext applicationContext: Context) : AppContainer {

    override val terminalRepository: Unit by lazy {
        // TODO: Create TerminalRepo interface
    }
    override val appContext = applicationContext
}

@Module
@InstallIn(ActivityComponent::class)
abstract class AppContainerModule {

    @Binds
    abstract fun bindAppContainerImplementation(
        appContainerImpl: AppContainerImpl
    ): AppContainer
}