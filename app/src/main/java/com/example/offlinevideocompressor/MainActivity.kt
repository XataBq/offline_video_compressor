package com.example.offlinevideocompressor

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.core_desing_system.theme.OfflineVideoCompressorTheme
import com.example.offlinevideocompressor.navigation.AppGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
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
