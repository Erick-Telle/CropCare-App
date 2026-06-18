package com.cropcare.feature.watering.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cropcare.feature.watering.history.WateringHistoryScreen

fun NavGraphBuilder.wateringGraph(
    onNavigateBack: () -> Unit
) {
    composable(
        route = WateringRoutes.WATERING_HISTORY,
        arguments = listOf(navArgument(WateringRoutes.ARG_PLANT_ID) { type = NavType.LongType })
    ) {
        WateringHistoryScreen(onNavigateBack = onNavigateBack)
    }
}
