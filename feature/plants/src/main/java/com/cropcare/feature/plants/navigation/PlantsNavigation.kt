package com.cropcare.feature.plants.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cropcare.feature.plants.catalog.SpeciesCatalogScreen
import com.cropcare.feature.plants.dashboard.DashboardScreen
import com.cropcare.feature.plants.detail.PlantDetailScreen
import com.cropcare.feature.plants.form.PlantFormScreen
import com.cropcare.feature.plants.form.PlantFormViewModel
import com.cropcare.feature.plants.onboarding.OnboardingClimateScreen
import com.cropcare.feature.plants.onboarding.OnboardingWelcomeScreen
import com.cropcare.feature.plants.splash.SplashScreen

fun NavGraphBuilder.plantsGraph(navController: NavHostController) {
    composable(PlantsRoutes.SPLASH) {
        SplashScreen(
            onNavigateToOnboarding = {
                navController.navigate(PlantsRoutes.ONBOARDING_WELCOME) {
                    popUpTo(PlantsRoutes.SPLASH) { inclusive = true }
                }
            },
            onNavigateToDashboard = {
                navController.navigate(PlantsRoutes.DASHBOARD) {
                    popUpTo(PlantsRoutes.SPLASH) { inclusive = true }
                }
            }
        )
    }

    composable(PlantsRoutes.ONBOARDING_WELCOME) {
        OnboardingWelcomeScreen(
            onContinue = { navController.navigate(PlantsRoutes.ONBOARDING_CLIMATE) }
        )
    }

    composable(PlantsRoutes.ONBOARDING_CLIMATE) {
        OnboardingClimateScreen(
            onComplete = {
                navController.navigate(PlantsRoutes.DASHBOARD) {
                    popUpTo(PlantsRoutes.ONBOARDING_WELCOME) { inclusive = true }
                }
            }
        )
    }

    composable(PlantsRoutes.DASHBOARD) {
        DashboardScreen(
            onAddPlant = { navController.navigate(PlantsRoutes.ADD_PLANT) },
            onPlantClick = { plantId ->
                navController.navigate(PlantsRoutes.plantDetailRoute(plantId))
            }
        )
    }

    composable(PlantsRoutes.ADD_PLANT) {
        PlantFormRoute(
            navController = navController,
            onSaveSuccess = { navController.popBackStack() }
        )
    }

    composable(
        route = PlantsRoutes.EDIT_PLANT,
        arguments = listOf(navArgument(PlantsRoutes.ARG_PLANT_ID) { type = NavType.LongType })
    ) {
        PlantFormRoute(
            navController = navController,
            onSaveSuccess = { navController.popBackStack() }
        )
    }

    composable(
        route = PlantsRoutes.PLANT_DETAIL,
        arguments = listOf(navArgument(PlantsRoutes.ARG_PLANT_ID) { type = NavType.LongType })
    ) {
        PlantDetailScreen(
            onNavigateBack = { navController.popBackStack() },
            onEditPlant = { plantId ->
                navController.navigate(PlantsRoutes.editPlantRoute(plantId))
            }
        )
    }

    composable(PlantsRoutes.SPECIES_CATALOG) {
        SpeciesCatalogScreen(
            onNavigateBack = { navController.popBackStack() },
            onSpeciesSelected = { speciesId, speciesName ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(PlantsRoutes.SELECTED_SPECIES_ID, speciesId)
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(PlantsRoutes.SELECTED_SPECIES_NAME, speciesName)
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun PlantFormRoute(
    navController: NavHostController,
    onSaveSuccess: () -> Unit,
    viewModel: PlantFormViewModel = hiltViewModel()
) {
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.getStateFlow<Long?>(PlantsRoutes.SELECTED_SPECIES_ID, null)
            ?.collect { speciesId ->
                if (speciesId != null) {
                    val speciesName = savedStateHandle.get<String>(PlantsRoutes.SELECTED_SPECIES_NAME) ?: ""
                    viewModel.onSpeciesSelected(speciesId, speciesName)
                    savedStateHandle.remove<Long>(PlantsRoutes.SELECTED_SPECIES_ID)
                    savedStateHandle.remove<String>(PlantsRoutes.SELECTED_SPECIES_NAME)
                }
            }
    }

    PlantFormScreen(
        onNavigateBack = { navController.popBackStack() },
        onNavigateToSpeciesCatalog = { navController.navigate(PlantsRoutes.SPECIES_CATALOG) },
        onSaveSuccess = onSaveSuccess,
        onSpeciesSelected = viewModel::onSpeciesSelected,
        viewModel = viewModel
    )
}
