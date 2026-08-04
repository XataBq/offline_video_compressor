package com.example.feature_video_compressor_ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_navigation_api.domain.FeatureNavGraph
import com.example.core_navigation_api.domain.StartDirectionProvider
import com.example.feature_video_compressor_ui.navigation.directions.VideoCompressorResultDirection
import com.example.feature_video_compressor_ui.navigation.directions.VideoCompressorStartDirectionProvider
import com.example.feature_video_compressor_ui.screens.VideoCompressorScreen
import com.example.feature_video_compressor_ui.screens.VideoCompressorResultScreen

class CompressorNavigationGraph : FeatureNavGraph {
    override val startDirection: StartDirectionProvider
        get() = VideoCompressorStartDirectionProvider

    override fun NavGraphBuilder.register(navController: NavController) {
        composable(startDirection.screenName) { backStackEntry ->
            VideoCompressorScreen(
                onClick = {}
            )
        }
        composable(VideoCompressorResultDirection.screenName) { backStackEntry ->
            VideoCompressorResultScreen()
        }
    }
}