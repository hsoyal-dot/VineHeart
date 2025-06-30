package com.example.wearos_heartrate.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HeartRateScreen(heartRateFlow: StateFlow<Float?>) {
    val heartRate by heartRateFlow.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Live Heart Rate", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = heartRate?.toInt()?.toString() ?: "Reading...",
                fontSize = 28.sp
            )
        }
    }
}