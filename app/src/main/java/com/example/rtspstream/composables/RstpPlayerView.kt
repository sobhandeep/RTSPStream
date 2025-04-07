package com.example.rtspstream.composables

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

@SuppressLint("RememberReturnType")
@Composable
fun RtspPlayerView(
    modifier: Modifier = Modifier,
    rtspUrl: String
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val libVLC = remember {
        LibVLC(context, arrayListOf("--network-caching=300", "--rtsp-tcp"))
    }
    val mediaPlayer = remember {
        MediaPlayer(libVLC)
    }

    DisposableEffect(Unit) {
        val media = Media(libVLC, rtspUrl.toUri()).apply {
            setHWDecoderEnabled(true, false)
        }

        mediaPlayer.media = media
        mediaPlayer.play()

        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
            media.release()
            libVLC.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            FrameLayout(it).apply {
                val videoLayout = VLCVideoLayout(it)
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                addView(videoLayout)
                mediaPlayer.attachViews(videoLayout, null, false, false)
            }
        },
        update = {
            // Called on recomposition
        }
    )
}