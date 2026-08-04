package com.example.offlinevideocompressor.navigation

import com.example.core_navigation_api.domain.FeatureNavGraph
import com.example.feature.video.compressor.ui.navigation.CompressorNavigationGraph

internal object FeatureGraphs {
    val FEATURE_GRAPHS: Set<FeatureNavGraph> = setOf(
        CompressorNavigationGraph(),
    )
}