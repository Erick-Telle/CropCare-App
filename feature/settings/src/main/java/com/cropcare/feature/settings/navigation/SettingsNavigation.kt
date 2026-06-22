package com.cropcare.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.cropcare.feature.settings.climate.ClimateConfigScreen
import com.cropcare.feature.settings.general.GeneralSettingsScreen

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    composable(SettingsRoutes.GENERAL_SETTINGS) {
        GeneralSettingsScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToClimate = { navController.navigate(SettingsRoutes.CLIMATE_CONFIG) }
        )
    }

    composable(SettingsRoutes.CLIMATE_CONFIG) {
        ClimateConfigScreen(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
