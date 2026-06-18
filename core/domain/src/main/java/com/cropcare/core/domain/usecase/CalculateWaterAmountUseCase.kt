package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.model.Species
import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateWaterAmountUseCase @Inject constructor() {

    operator fun invoke(species: Species, climate: ClimateConfig): Int {
        val factor = ClimateAdjustmentFactors.temperatureMultiplier(climate.temperaturaPromedio) *
            ClimateAdjustmentFactors.humidityWaterMultiplier(climate.humedadAmbiental) *
            ClimateAdjustmentFactors.seasonMultiplier(climate.estacionActual)

        return (species.cantidadBaseAguaMl * factor)
            .roundToInt()
            .coerceIn(MIN_WATER, MAX_WATER)
    }

    companion object {
        private const val MIN_WATER = 30
        private const val MAX_WATER = 2000
    }
}
