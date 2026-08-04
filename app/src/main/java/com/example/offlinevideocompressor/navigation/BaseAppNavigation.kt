package com.example.offlinevideocompressor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core_navigation_api.domain.FeatureNavGraph

@Composable
internal fun BaseAppNavigation(
    navController: NavHostController,
    featureGraphs: Set<FeatureNavGraph>,
    startDirectionProvider: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDirectionProvider,
    ) {
        featureGraphs.forEach { graph ->
            with(graph) {
                register(navController)
            }
        }
    }
}