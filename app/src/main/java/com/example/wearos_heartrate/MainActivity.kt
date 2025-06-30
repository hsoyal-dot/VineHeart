package com.example.wearos_heartrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wearos_heartrate.sensor.HeartRateSensorManager
import com.example.wearos_heartrate.ui.HeartRateScreen


class MainActivity : ComponentActivity() {

    private lateinit var heartRateSensorManager: HeartRateSensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        heartRateSensorManager = HeartRateSensorManager(this)
        heartRateSensorManager.registerListener()

        setContent {
            HeartRateScreen(heartRateFlow = heartRateSensorManager.heartRateFlow)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        heartRateSensorManager.unregisterListener()
    }
}