package com.example.wearos_heartrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.wearos_heartrate.auth.AuthScreen
import com.example.wearos_heartrate.sensor.HeartRateSensorManager
import com.example.wearos_heartrate.ui.HeartRateScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var heartRateSensorManager: HeartRateSensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        heartRateSensorManager = HeartRateSensorManager(this)

        setContent {
            val auth = FirebaseAuth.getInstance()
            var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

            if (!isLoggedIn) {
                AuthScreen(
                    onAuthSuccess = {
                        isLoggedIn = true
                        heartRateSensorManager.registerListener()
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    heartRateSensorManager.registerListener()
                }

                HeartRateScreen(
                    heartRateFlow = heartRateSensorManager.heartRateFlow,
                    onSendClick = { heartRateSensorManager.sendLatestHeartRateManually() },
                    onLogoutClick = { heartRateSensorManager.unregisterListener() }
                )

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        heartRateSensorManager.unregisterListener()
    }
}