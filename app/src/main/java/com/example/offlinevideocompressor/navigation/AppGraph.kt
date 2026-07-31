package com.example.offlinevideocompressor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.feature_video_compressor_ui.navigation.directions.VideoCompressorStartDirectionProvider

@Composable
internal fun AppGraph() {
    val navController = rememberNavController()
    BaseAppNavigation(
        navController = navController,
        featureGraphs = FeatureGraphs.FEATURE_GRAPHS,
        startDirectionProvider = VideoCompressorStartDirectionProvider,
    )
}