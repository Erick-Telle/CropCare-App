package com.cropcare.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object OnboardingWelcome : Screen("onboarding_welcome")
    data object OnboardingClimate : Screen("onboarding_climate")
    data object Dashboard : Screen("dashboard")
    data object AddPlant : Screen("add_plant")
    data object EditPlant : Screen("edit_plant/{plantId}") {
        fun createRoute(plantId: Long) = "edit_plant/$plantId"
    }
    data object PlantDetail : Screen("plant_detail/{plantId}") {
        fun createRoute(plantId: Long) = "plant_detail/$plantId"
    }
    data object SpeciesCatalog : Screen("species_catalog")

    companion object {
        const val ARG_PLANT_ID = "plantId"
    }
}
