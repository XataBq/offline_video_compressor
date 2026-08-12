package com.example.feature.video.compressor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core_navigation_api.domain.FeatureNavGraph
import com.example.core_navigation_api.domain.StartDirectionProvider
import com.example.feature.video.compressor.ui.navigation.directions.VideoCompressionProgressDirection
import com.example.feature.video.compressor.ui.navigation.directions.VideoCompressorResultDirection
import com.example.feature.video.compressor.ui.navigation.directions.VideoCompressorSettingsDirection
import com.example.feature.video.compressor.ui.navigation.directions.VideoCompressorStartDirectionProvider
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel
import com.example.feature.video.compressor.ui.presentation.screens.VideoCompressorResultScreen
import com.example.feature.video.compressor.ui.presentation.screens.compressionprogress.VideoCompressionProgressRoute
import com.example.feature.video.compressor.ui.presentation.screens.videocompressorsettings.VideoCompressorSettingsRoute
import com.example.feature.video.compressor.ui.presentation.screens.videocompressorstart.VideoCompressorRoute

class CompressorNavigationGraph : FeatureNavGraph {
    override val startDirection: StartDirectionProvider
        get() = VideoCompressorStartDirectionProvider

    override fun NavGraphBuilder.register(navController: NavController) {
        navigation(
            route = COMPRESSOR_GRAPH_ROUTE,
            startDestination = startDirection.screenName,
        ) {
            composable(startDirection.screenName) { backStackEntry ->
                val viewModel = sharedGraphViewModel<VideoCompressorViewModel>(
                    navController = navController,
                    backStackEntry = backStackEntry,
                )
                VideoCompressorRoute(
                    viewModel = viewModel,
                    navigateSettings = {},
                    navigateCompressionSettings = {
                        navController.navigate(VideoCompressorSettingsDirection.screenName)
                    }
                )
            }
            composable(VideoCompressorResultDirection.screenName) { backStackEntry ->
                VideoCompressorResultScreen()
            }
            composable(VideoCompressorSettingsDirection.screenName) { backStackEntry ->
                val viewModel = sharedGraphViewModel<VideoCompressorViewModel>(
                    navController = navController,
                    backStackEntry = backStackEntry,
                )
                VideoCompressorSettingsRoute(
                    viewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateCompressionProgress = {
                        navController.navigate(VideoCompressionProgressDirection.screenName)
                    },
                    navigateHelp = {},
                    )
            }
            composable(VideoCompressionProgressDirection.screenName) { backStackEntry ->
                val viewModel = sharedGraphViewModel<VideoCompressorViewModel>(
                    navController = navController,
                    backStackEntry = backStackEntry
                )
                VideoCompressionProgressRoute(
                    viewModel = viewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateHelp = {},
                )
            }
        }
    }

    companion object {
        const val COMPRESSOR_GRAPH_ROUTE = "compressor_graph"
    }
}

@Composable
private inline fun <reified VM: ViewModel> sharedGraphViewModel(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
): VM {
    val graphBackStackEntry = remember(backStackEntry) {
        navController.getBackStackEntry(CompressorNavigationGraph.COMPRESSOR_GRAPH_ROUTE)
    }
    return hiltViewModel(graphBackStackEntry)
}