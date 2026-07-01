package com.cropcare.feature.plants.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cropcare.feature.catalog.navigation.CatalogRoutes
import com.cropcare.feature.plants.catalog.SpeciesCatalogScreen
import com.cropcare.feature.plants.dashboard.DashboardScreen
import com.cropcare.feature.plants.detail.PlantDetailScreen
import com.cropcare.feature.plants.form.PlantFormScreen
import com.cropcare.feature.plants.form.PlantFormViewModel
import com.cropcare.feature.plants.onboarding.OnboardingClimateScreen
import com.cropcare.feature.plants.onboarding.OnboardingWelcomeScreen
import com.cropcare.feature.plants.splash.SplashScreen
import com.cropcare.feature.settings.navigation.SettingsRoutes
import com.cropcare.feature.watering.navigation.WateringRoutes

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
            },
            onOpenSettings = { navController.navigate(SettingsRoutes.GENERAL_SETTINGS) },
            onOpenSpeciesCatalog = { navController.navigate(PlantsRoutes.SPECIES_CATALOG_BROWSE) }
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
            },
            onViewWateringHistory = { plantId ->
                navController.navigate(WateringRoutes.wateringHistoryRoute(plantId))
            }
        )
    }

    composable(PlantsRoutes.SPECIES_CATALOG_BROWSE) {
        SpeciesCatalogScreen(
            onNavigateBack = { navController.popBackStack() },
            onSpeciesSelected = { speciesId, _ ->
                navController.navigate(CatalogRoutes.careAdviceRoute(speciesId))
            },
            browseMode = true
        )
    }

    composable(PlantsRoutes.SPECIES_CATALOG) {
        SpeciesCatalogScreen(
            onNavigateBack = { navController.popBackStack() },
            onSpeciesSelected = { speciesId, speciesName ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.apply {
                        set(PlantsRoutes.SELECTED_SPECIES_NAME, speciesName)
                        set(PlantsRoutes.SELECTED_SPECIES_ID, speciesId)
                    }
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
    val selectedSpeciesId by savedStateHandle
        ?.getStateFlow<Long?>(PlantsRoutes.SELECTED_SPECIES_ID, null)
        ?.collectAsStateWithLifecycle(initialValue = null)
        ?: remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(selectedSpeciesId) {
        val speciesId = selectedSpeciesId ?: return@LaunchedEffect
        val speciesName = savedStateHandle?.get<String>(PlantsRoutes.SELECTED_SPECIES_NAME).orEmpty()
        viewModel.onSpeciesSelected(speciesId, speciesName)
        savedStateHandle?.remove<Long>(PlantsRoutes.SELECTED_SPECIES_ID)
        savedStateHandle?.remove<String>(PlantsRoutes.SELECTED_SPECIES_NAME)
    }

    PlantFormScreen(
        onNavigateBack = { navController.popBackStack() },
        onNavigateToSpeciesCatalog = { navController.navigate(PlantsRoutes.SPECIES_CATALOG) },
        onSaveSuccess = onSaveSuccess,
        onSpeciesSelected = viewModel::onSpeciesSelected,
        viewModel = viewModel
    )
}
