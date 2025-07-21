package com.example.wearos_heartrate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HeartRateScreen(
    heartRateFlow: StateFlow<Float?>,
    onSendClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val heartRate by heartRateFlow.collectAsState()
    val auth = FirebaseAuth.getInstance()
    var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Live Heart Rate", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = heartRate?.toInt()?.toString() ?: "Reading...",
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    modifier = Modifier.height(36.dp),
                    onClick = { onSendClick() }
                ) {
                    Text("Send BPM", fontSize = 12.sp)
                }

                Button(
                    modifier = Modifier.height(36.dp),
                    onClick = {
                        auth.signOut()
                        isLoggedIn = false
                        onLogoutClick()
                    }
                ) {
                    Text("Logout", fontSize = 12.sp)
                }
            }
        }
    }
}