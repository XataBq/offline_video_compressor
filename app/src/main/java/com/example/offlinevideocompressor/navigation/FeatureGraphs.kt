package com.example.offlinevideocompressor.navigation

import com.example.core_navigation_api.domain.FeatureNavGraph
import com.example.feature_video_compressor_ui.navigation.CompressorNavigationGraph

internal object FeatureGraphs {
    val FEATURE_GRAPHS: Set<FeatureNavGraph> = setOf(
        CompressorNavigationGraph(),
    )
}