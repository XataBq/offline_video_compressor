package com.example.offlinevideocompressor.navigation

import com.example.core_navigation_api.domain.FeatureNavGraph
import com.example.feature.video.compressor.ui.navigation.CompressorNavigationGraph

internal object NavigationGraphs {
    val GRAPHS: Set<FeatureNavGraph> =
        setOf(
            AppNavigationGraph(),
            CompressorNavigationGraph(),
        )
}