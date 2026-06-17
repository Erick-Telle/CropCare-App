package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.repository.ClimateRepository
import com.cropcare.core.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateWateringParamsUseCase @Inject constructor(
    private val speciesRepository: SpeciesRepository,
    private val climateRepository: ClimateRepository
) {
    suspend operator fun invoke(speciesId: Long): Pair<Int, Int> {
        val species = speciesRepository.getSpeciesById(speciesId).first()
            ?: return DEFAULT_FREQUENCY to DEFAULT_WATER
        val climate = climateRepository.getClimateConfig().first()

        val tempFactor = climate?.let {
            1f + (it.temperaturaPromedio - 22f) * species.ajustePorTemperatura / 10f
        } ?: 1f

        val humidityFactor = climate?.let {
            1f + (50f - it.humedadAmbiental) * species.ajustePorHumedad / 50f
        } ?: 1f

        val frequency = (species.frecuenciaBaseDias / (tempFactor * humidityFactor))
            .roundToInt()
            .coerceIn(MIN_FREQUENCY, MAX_FREQUENCY)

        val waterAmount = (species.cantidadBaseAguaMl * tempFactor)
            .roundToInt()
            .coerceIn(MIN_WATER, MAX_WATER)

        return frequency to waterAmount
    }

    suspend fun calculateForSpecies(species: Species): Pair<Int, Int> =
        invoke(species.id)

    companion object {
        private const val DEFAULT_FREQUENCY = 7
        private const val DEFAULT_WATER = 200
        private const val MIN_FREQUENCY = 1
        private const val MAX_FREQUENCY = 30
        private const val MIN_WATER = 50
        private const val MAX_WATER = 1000
    }
}
