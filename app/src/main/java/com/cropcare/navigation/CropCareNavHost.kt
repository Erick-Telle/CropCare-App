package com.cropcare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cropcare.feature.plants.navigation.PlantsRoutes
import com.cropcare.feature.plants.navigation.plantsGraph
import com.cropcare.feature.settings.navigation.settingsGraph
import com.cropcare.feature.watering.navigation.wateringGraph

@Composable
fun CropCareNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = PlantsRoutes.SPLASH
    ) {
        plantsGraph(navController)
        settingsGraph(navController)
        wateringGraph(onNavigateBack = { navController.popBackStack() })
    }
}
