package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.model.Species
import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateWateringFrequencyUseCase @Inject constructor() {

    operator fun invoke(species: Species, climate: ClimateConfig): Int {
        val factor = ClimateAdjustmentFactors.temperatureMultiplier(climate.temperaturaPromedio) *
            ClimateAdjustmentFactors.humidityMultiplier(climate.humedadAmbiental) *
            ClimateAdjustmentFactors.seasonMultiplier(climate.estacionActual)

        return (species.frecuenciaBaseDias * factor)
            .roundToInt()
            .coerceIn(MIN_FREQUENCY, MAX_FREQUENCY)
    }

    companion object {
        private const val MIN_FREQUENCY = 1
        private const val MAX_FREQUENCY = 60
    }
}
