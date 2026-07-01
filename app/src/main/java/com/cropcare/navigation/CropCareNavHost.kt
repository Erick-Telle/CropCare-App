package com.cropcare.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cropcare.feature.catalog.navigation.catalogGraph
import com.cropcare.feature.plants.navigation.PlantsRoutes
import com.cropcare.feature.plants.navigation.plantsGraph
import com.cropcare.feature.settings.navigation.settingsGraph
import com.cropcare.feature.watering.navigation.wateringGraph

@Composable
fun CropCareNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PlantsRoutes.SPLASH,
        modifier = modifier
    ) {
        plantsGraph(navController)
        settingsGraph(navController)
        wateringGraph(onNavigateBack = { navController.popBackStack() })
        catalogGraph(onNavigateBack = { navController.popBackStack() })
    }
}
