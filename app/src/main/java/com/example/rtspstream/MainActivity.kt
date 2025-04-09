package com.example.rtspstream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.rtspstream.composables.VideoScreen
import com.example.rtspstream.ui.theme.RTSPStreamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RTSPStreamTheme {
                VideoScreen()
            }
        }
    }
}
