package com.example.rtspstream.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.core.net.toUri
import com.example.rtspstream.MainActivity
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer

@Composable
fun VideoScreen(libVLC: LibVLC, mediaPlayer: MediaPlayer) {
    val context = LocalContext.current
    var rtspUrl by remember { mutableStateOf("rtsp://192.168.226.186:5540/ch0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = rtspUrl,
            onValueChange = { rtspUrl = it },
            label = { Text("RTSP URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        RtspPlayerView(
            rtspUrl = rtspUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val filePath = "${context.filesDir}/recorded.mp4"
                val media = Media(libVLC, rtspUrl.toUri()).apply {
                    addOption(":sout=#file{dst=$filePath}")
                    addOption(":sout-keep")
                }
                mediaPlayer.media = media
                mediaPlayer.play()
            }) {
                Text("Record")
            }

            Button(onClick = {
                (context as? MainActivity)?.enterPipMode()
            }) {
                Text("Pop Out")
            }
        }
    }
}
