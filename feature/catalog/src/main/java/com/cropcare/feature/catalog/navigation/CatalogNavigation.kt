package com.cropcare.feature.catalog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cropcare.feature.catalog.care.CareAdviceScreen

fun NavGraphBuilder.catalogGraph(onNavigateBack: () -> Unit) {
    composable(
        route = CatalogRoutes.CARE_ADVICE,
        arguments = listOf(
            navArgument(CatalogRoutes.ARG_ESPECIE_ID) { type = NavType.LongType }
        )
    ) {
        CareAdviceScreen(onNavigateBack = onNavigateBack)
    }
}
