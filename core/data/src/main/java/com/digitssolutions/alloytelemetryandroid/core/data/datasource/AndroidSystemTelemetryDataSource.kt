package com.digitssolutions.alloytelemetryandroid.core.data.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.PowerManager
import android.telephony.CellSignalStrengthLte
import android.telephony.ServiceState
import android.telephony.SignalStrength
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.digitssolutions.alloytelemetryandroid.core.data.model.TelemetryDataEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
class AndroidSystemTelemetryDataSource @Inject constructor (
    @param:ApplicationContext private val context: Context
) : SystemTelemetryDataSource {
    private val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
    private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    override fun observeRawTelemetry(): Flow<TelemetryDataEntity> = callbackFlow {

        // 👈 PLACE IT HERE: Check live thermal status immediately when the flow starts
        val currentSystemThermal = powerManager?.currentThermalStatus ?: -1
        android.util.Log.d("TelemetryDebug", "Live system thermal status from OS on start: $currentSystemThermal")

        //  1. Single Source of Truth: Initialize the rolling accumulator with real starting values
        var currentEntity = TelemetryDataEntity(
            rawThermalStatus = powerManager?.currentThermalStatus ?: PowerManager.THERMAL_STATUS_NONE,
            rsrpDbm = 0,
            wifiRssi = 0,
            isCellularConnected = false,
            isWifiConnected = false
        )

        // 2. Thermal listener updates only the thermal field
        val thermalListener = PowerManager.OnThermalStatusChangedListener { status ->
            android.util.Log.d("TelemetryDebug", "Thermal listener triggered! New status: $status")
            currentEntity = currentEntity.copy(rawThermalStatus = status)
            trySend(currentEntity) // Emit the accumulated state
        }

        // 3. Telephony Listener updates cellular signals and state
        val telephonyCallback = object : TelephonyCallback(),
            TelephonyCallback.SignalStrengthsListener,
            TelephonyCallback.ServiceStateListener {

            override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                // Non-blocking emission into the flow
                currentEntity = currentEntity.copy(rsrpDbm = extractRsrp(signalStrength))
                trySend(currentEntity)
            }

            override fun onServiceStateChanged(serviceState: ServiceState) {
                currentEntity = currentEntity.copy(isCellularConnected = serviceState.state == ServiceState.STATE_IN_SERVICE)
                trySend(currentEntity)
            }
        }

        // 2. Local helper function (Has access to currentEntity and trySend via closure!)
        fun updateAndEmitConnectivity() {
            val activeNetwork = connectivityManager?.activeNetwork
            val capabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)
            val hasCellular = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            val hasWifi = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

            // Extract Wi-Fi RSSI from NetworkCapabilities transport info (API 29+)
            val wifiInfo = capabilities?.transportInfo as? android.net.wifi.WifiInfo
            val rssi = wifiInfo?.rssi ?: 0

            currentEntity = currentEntity.copy(
                isCellularConnected = hasCellular,
                isWifiConnected = hasWifi,
                wifiRssi = rssi
            )
            trySend(currentEntity)          // replaces last-type one-Shot update, at the launch, but step #4, below, give live udpates
        }

        // 4. Create the live Network Callback,  Reusable helper that updates the rolling state and emits downstream
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                updateAndEmitConnectivity()             // <--- This updates currentEntity and calls trySend()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                updateAndEmitConnectivity()
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                updateAndEmitConnectivity()
            }
        }

        // Register listeners...
        powerManager?.addThermalStatusListener(context.mainExecutor, thermalListener)
        telephonyManager?.registerTelephonyCallback(context.mainExecutor, telephonyCallback)
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)

        // 6. Capture initial network state on launch
        updateAndEmitConnectivity()

        // FORCE INITIAL EMISSION: Ensure thermal sate is pushed immediately on collection start
        trySend(currentEntity)

        // Unregister listeners upon scope cancellation
        awaitClose {
            powerManager?.removeThermalStatusListener(thermalListener)
            telephonyManager?.unregisterTelephonyCallback(telephonyCallback)
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }

    }


    private fun extractRsrp(signalStrength: SignalStrength?): Int {
        if (signalStrength == null) return 0
        return signalStrength.cellSignalStrengths
            .filterIsInstance<CellSignalStrengthLte>()
            .firstOrNull()
            ?.rsrp ?: 0
    }

}