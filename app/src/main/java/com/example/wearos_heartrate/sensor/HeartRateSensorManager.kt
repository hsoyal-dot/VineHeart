package com.example.wearos_heartrate.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.wearos_heartrate.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.json.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class HeartRateSensorManager(private val context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val heartRateSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

    private var sendAfter = 60_000L
    private var lastSent: Long = 0

    private val _heartRateFlow = MutableStateFlow<Float?>(null)
    val heartRateFlow = _heartRateFlow.asStateFlow()

    fun registerListener() {
        heartRateSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_HEART_RATE) {
            val heartRate = event.values.firstOrNull()
            _heartRateFlow.value = heartRate

            heartRate?.let {
                var currentTime = System.currentTimeMillis()
                if (currentTime - lastSent >= sendAfter) {
                    lastSent = currentTime
                    CoroutineScope(Dispatchers.IO).launch {
                        sendHeartRate(it)
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private suspend fun sendHeartRate(heartRate: Float) {
        val json = JSONObject()
        json.put("device_id", "watch_1")
        json.put("timestamp", System.currentTimeMillis())
        json.put("bpm", heartRate)

        val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BuildConfig.SUPABASE_URL)
            .addHeader(
                "apikey",
                BuildConfig.SUPABASE_API_KEY
            )
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.SUPABASE_API_KEY}"
            )
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        Log.d("Supabase Sync", "Heart Rate Sent: $heartRate, Response: ${response.code}")
    }
}
