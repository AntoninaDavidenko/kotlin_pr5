package com.example.labfive.ui.calculator2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Calculator2(
    goBack: () -> Unit
) {
    var emergencyLosses by remember { mutableStateOf("") }
    var plannedLosses by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun calculate2() {
        val formattedEmergencyLosses = emergencyLosses.toDoubleOrNull() ?: 0.0
        val formattedPlannedLosses = plannedLosses.toDoubleOrNull() ?: 0.0
        val omega = 0.01
        val tB = 0.045
        val pM = 5120.0
        val tM = 6451.0
        val kP = 0.004


        val mWEmergency = omega * tB * pM * tM
        val mWPlanned = kP * pM * tM
        val mLosses = (formattedEmergencyLosses * mWEmergency) + formattedPlannedLosses * mWPlanned

        result = (mLosses).toString()
    }

    Column(modifier = Modifier.padding(all = 15.dp)) {
        TextField(
            value = emergencyLosses,
            onValueChange = { emergencyLosses = it },
            label = { Text("Збитки у разі аварійних вимкнень") },
            maxLines = 1,
        )
        TextField(
            value = plannedLosses,
            onValueChange = { plannedLosses = it },
            label = { Text("Збитки у разі планових вимкнень") },
            maxLines = 1,
        )
        Button(
            onClick = { calculate2() }
        ) {
            Text("Calculate")
        }
        if (result.isNotEmpty()) {
            Text("Математичне сподівання збитків від переривання електропостачання: $result грн.")
        }
        Box(
            modifier = Modifier.padding(top = 100.dp)
        ) {
            Button(
                onClick = goBack
            ) {
                Text("Go back")
            }
        }
    }
}