package com.example.offlinevideocompressor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_navigation_api.directions.AppSettingsDirection
import com.example.core_navigation_api.domain.FeatureNavGraph
import com.example.core_navigation_api.domain.StartDirectionProvider
import com.example.offlinevideocompressor.presentation.screens.settings.AppSettingsRoute

internal class AppNavigationGraph : FeatureNavGraph {

    override val startDirection: StartDirectionProvider
        get() = AppSettingsDirection

    override fun NavGraphBuilder.register(
        navController: NavController,
    ) {
        composable(AppSettingsDirection.screenName,) {
            AppSettingsRoute(
                navigateBack = { navController.popBackStack() },
            )
        }
    }

}