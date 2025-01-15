package com.example.paymentapp.connectivity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner


internal fun getRequiredBluetoothPermissions(): List<String> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        emptyList()
    }
}

@Composable
fun BluetoothScanObserver(
    lifecycleOwner: LifecycleOwner,
    bluetoothObserver: BluetoothReceiver,
    registerObserver: Boolean = false,
    unregisterObserver: Boolean = false
) {
    val context = LocalContext.current
    val bluetoothFilter = IntentFilter().apply {
        addAction(BluetoothDevice.ACTION_FOUND)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
    }

    if (registerObserver) {
        context.registerReceiver(bluetoothObserver, bluetoothFilter)
    }

    DisposableEffect(key1 = lifecycleOwner) {
        // Code to execute when the effect enters the composition
        val observer = LifecycleEventObserver { source, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                context.unregisterReceiver(bluetoothObserver)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

class BluetoothReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, action: Intent?) {
        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            // TODO:Callback onDiscoveryStarted
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            // TODO: Callback onDiscovery Finished
        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // TODO: Callback on device found
        }

    }
}