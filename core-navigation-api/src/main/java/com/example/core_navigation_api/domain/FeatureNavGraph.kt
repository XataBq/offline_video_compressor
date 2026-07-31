package com.example.core_navigation_api.domain

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureNavGraph {
    val startDirection: StartDirectionProvider

    fun NavGraphBuilder.register (
        navController: NavController
    )
}