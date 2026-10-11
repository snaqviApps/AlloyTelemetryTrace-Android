package com.digitssolutions.alloytelemetryandroid.core.data.datasource

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.PowerManager
import android.telephony.CellSignalStrengthLte
import android.telephony.ServiceState
import android.telephony.SignalStrength
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.digitssolutions.alloytelemetryandroid.core.data.model.TelemetryDataEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
class AndroidSystemTelemetryDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SystemTelemetryDataSource {

    private val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
    private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    override fun observeRawTelemetry(): Flow<TelemetryDataEntity> = callbackFlow {

        // 1. Single Source of Truth: Initialize the rolling accumulator with real starting values
        var currentEntity = TelemetryDataEntity(
            rawThermalStatus = powerManager?.currentThermalStatus ?: PowerManager.THERMAL_STATUS_NONE,
            rsrpDbm = 0,
            wifiRssi = 0,
            isCellularConnected = false,
            isWifiConnected = false
        )

        // 2. Thermal listener updates only the thermal field
        val thermalListener = PowerManager.OnThermalStatusChangedListener { status ->
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

        // 4. Register listeners...
        powerManager?.addThermalStatusListener(context.mainExecutor, thermalListener)
        telephonyManager?.registerTelephonyCallback(context.mainExecutor, telephonyCallback)

        // 5. Apply Initial Connectivity ONE-TIME Snapshot directly to currentEntity
        updateConnectivityState { cellular, wifi ->
            currentEntity = currentEntity.copy(
                isCellularConnected = cellular,
                isWifiConnected = wifi
            )
            trySend(currentEntity)
        }

        // 6. Unregister listeners upon scope cancellation
        awaitClose {
            powerManager?.removeThermalStatusListener(thermalListener)
            telephonyManager?.unregisterTelephonyCallback(telephonyCallback)
        }

    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun updateConnectivityState(onStateChanged: (cellular: Boolean, wifi: Boolean) -> Unit )
    {
        val activeNetwork = connectivityManager?.activeNetwork
        val capabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)
        val hasCellular = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
        val hasWifi = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

        onStateChanged(hasCellular, hasWifi)
    }

    private fun extractRsrp(signalStrength: SignalStrength?): Int {
        if (signalStrength == null) return 0
        return signalStrength.cellSignalStrengths
            .filterIsInstance<CellSignalStrengthLte>()
            .firstOrNull()
            ?.rsrp ?: 0
    }

}