package com.example.rtspstream.composables

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun RTSPPlayer(
    rtspUrl: String,
    context: Context,
    modifier: Modifier = Modifier
) {
    val player = remember(rtspUrl) {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(rtspUrl)
            val mediaSource = RtspMediaSource.Factory().createMediaSource(mediaItem)
            setMediaSource(mediaSource)
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(key1 = player) {
        onDispose {
            player.release()
        }
    }

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = {
            PlayerView(context).apply {
                useController = true
                this.player = player
            }
        }
    )
}
