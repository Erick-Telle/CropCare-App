package com.cropcare.feature.plants.navigation

object PlantsRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING_WELCOME = "onboarding_welcome"
    const val ONBOARDING_CLIMATE = "onboarding_climate"
    const val DASHBOARD = "dashboard"
    const val ADD_PLANT = "add_plant"
    const val EDIT_PLANT = "edit_plant/{plantId}"
    const val PLANT_DETAIL = "plant_detail/{plantId}"
    const val SPECIES_CATALOG = "species_catalog"
    const val SPECIES_CATALOG_BROWSE = "species_catalog_browse"

    const val ARG_PLANT_ID = "plantId"
    const val SELECTED_SPECIES_ID = "selected_species_id"
    const val SELECTED_SPECIES_NAME = "selected_species_name"

    fun editPlantRoute(plantId: Long) = "edit_plant/$plantId"
    fun plantDetailRoute(plantId: Long) = "plant_detail/$plantId"
}
