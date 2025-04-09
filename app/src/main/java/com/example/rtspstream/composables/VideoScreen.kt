package com.example.rtspstream.composables

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.rtspstream.MainActivity

@Composable
fun VideoScreen() {
    val context = LocalContext.current
    val activity = context as? MainActivity
    var rtspUrl by remember { mutableStateOf("rtsp://192.168.170.46:5540/ch0") }
    var playStream by remember { mutableStateOf(false) }

    val isInPipMode by MainActivity.isInPipModeState

    if (isInPipMode && playStream) {
        RTSPPlayer(rtspUrl = rtspUrl, context = context)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = rtspUrl,
            onValueChange = {
                rtspUrl = it
                playStream = false // stop current playback
            },
            label = { Text("RTSP URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { playStream = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Play Stream")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (playStream) {
            RTSPPlayer(
                rtspUrl = rtspUrl,
                context = context,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { activity?.enterPipMode() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enter PiP Mode")
        }
    }
}