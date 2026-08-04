package com.example.offlinevideocompressor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.core_desing_system.theme.OfflineVideoCompressorTheme
import com.example.offlinevideocompressor.navigation.AppGraph

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
