package com.example.offlinevideocompressor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.offlinevideocompressor.navigation.AppGraph
import com.example.offlinevideocompressor.ui.theme.OfflineVideoCompressorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OfflineVideoCompressorTheme {
                AppGraph()
            }
        }
    }
}
