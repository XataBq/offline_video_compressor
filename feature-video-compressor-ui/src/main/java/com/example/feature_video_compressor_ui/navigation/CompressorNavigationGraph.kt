package com.example.feature_video_compressor_ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_navigation_api.domain.FeatureNavGraph
import com.example.core_navigation_api.domain.StartDirectionProvider
import com.example.feature_video_compressor_ui.navigation.directions.VideoCompressorResultDirection
import com.example.feature_video_compressor_ui.navigation.directions.VideoCompressorStartDirectionProvider
import com.example.feature_video_compressor_ui.videoCompressor.VideoCompressorScreen
import com.example.feature_video_compressor_ui.videoCompressorResult.VideoCompressorResultScreen

class CompressorNavigationGraph : FeatureNavGraph {
    override val startDirection: StartDirectionProvider
        get() = VideoCompressorStartDirectionProvider

    override fun NavGraphBuilder.register(navController: NavController) {
        composable(startDirection.screenName) { backStackEntry ->
            VideoCompressorScreen()
        }
        composable(VideoCompressorResultDirection.screenName) { backStackEntry ->
            VideoCompressorResultScreen()
        }
    }
}