package com.cropcare.feature.catalog.navigation

object CatalogRoutes {
    const val CARE_ADVICE = "care_advice/{especieId}"
    const val ARG_ESPECIE_ID = "especieId"

    fun careAdviceRoute(especieId: Long) = "care_advice/$especieId"
}
