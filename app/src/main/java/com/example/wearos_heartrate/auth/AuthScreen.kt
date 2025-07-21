package com.example.wearos_heartrate.auth

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(onAuthSuccess: () -> Unit) {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login to VineHeart", fontSize = 12.sp)

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontSize = 10.sp) },
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", fontSize = 10.sp) },
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Button(onClick = {
            scope.launch {
                auth.signInWithEmailAndPassword(email.trim(), password)
                    .addOnSuccessListener {
                        Log.d("AuthScreen", "Login success")
                        onAuthSuccess()
                    }
                    .addOnFailureListener {
                        val message = when (it.localizedMessage) {
                            "The password is invalid or the user does not have a password." ->
                                "Incorrect password. Try again."
                            "There is no user record corresponding to this identifier. The user may have been deleted." ->
                                "No account found with this email."
                            else -> it.localizedMessage ?: "Login failed."
                        }
                        Log.e("AuthScreen", "Login failed: $message")
                        error = message
                    }
            }
        }) {
            Text("Login", fontSize = 10.sp)
        }

        error?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error, fontSize = 10.sp)
        }
    }
}