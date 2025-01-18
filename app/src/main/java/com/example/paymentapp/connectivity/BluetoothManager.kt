package com.example.paymentapp.connectivity

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.IntentCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

const val BLUETOOTH_DISCOVERY_PERIOD_MILLIS = 500L

internal fun getRequiredBluetoothPermissions(): List<String> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    } else {
        emptyList()
    }
}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothScanObserver(
    lifecycleOwner: LifecycleOwner,
    bluetoothObserver: BluetoothReceiver?,
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
        val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter = bluetoothManager.adapter
        //TODO: Perform permission check here first. Remove annotation to suppress.
        bluetoothAdapter.startDiscovery()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                bluetoothAdapter.cancelDiscovery()
            },
            BLUETOOTH_DISCOVERY_PERIOD_MILLIS
        )

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

class BluetoothReceiver(
    private val onDiscoveryStart: () -> Unit,
    private val onDiscoveryComplete: (List<BluetoothDevice>) -> Unit
) : BroadcastReceiver() {
    private val discoveredDevices: MutableList<BluetoothDevice> = mutableListOf()

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED == action) {
            onDiscoveryStart.invoke()
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
            onDiscoveryComplete.invoke(discoveredDevices)
        } else if (BluetoothDevice.ACTION_FOUND == action) {
            val device = IntentCompat.getParcelableExtra(
                intent,
                BluetoothDevice.EXTRA_DEVICE,
                BluetoothDevice::class.java
            )

            // Only add a BluetoothDevice if the name is not empty and if one with the same
            // address does not already exist.
            if (device?.name?.isNotEmpty() == true) {
                val existingDevice = discoveredDevices.find { addedDevice ->
                    device.address == addedDevice.address
                }
                if (existingDevice == null) {
                    discoveredDevices.add(device)
                }
            }
        }
    }
}