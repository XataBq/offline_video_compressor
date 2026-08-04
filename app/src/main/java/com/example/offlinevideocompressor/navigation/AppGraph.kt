package com.example.offlinevideocompressor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.video.compressor.ui.navigation.CompressorNavigationGraph

@Composable
internal fun AppGraph() {
    val navController = rememberNavController()
    BaseAppNavigation(
        navController = navController,
        featureGraphs = FeatureGraphs.FEATURE_GRAPHS,
        startDirectionProvider = CompressorNavigationGraph.COMPRESSOR_GRAPH_ROUTE,
    )
}