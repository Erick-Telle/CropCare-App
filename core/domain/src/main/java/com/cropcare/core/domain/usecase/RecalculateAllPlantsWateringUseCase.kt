package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RecalculateAllPlantsWateringUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val speciesRepository: SpeciesRepository,
    private val calculateWateringFrequencyUseCase: CalculateWateringFrequencyUseCase,
    private val calculateWaterAmountUseCase: CalculateWaterAmountUseCase
) {
    suspend operator fun invoke(climateConfig: ClimateConfig) {
        val plants = plantRepository.getAllPlants().first().filter { it.activa }

        plants.forEach { plant ->
            val species = speciesRepository.getSpeciesById(plant.especieId).first() ?: return@forEach
            val frequency = calculateWateringFrequencyUseCase(species, climateConfig)
            val amount = calculateWaterAmountUseCase(species, climateConfig)

            plantRepository.updatePlant(
                plant.copy(
                    frecuenciaRiegoDias = frequency,
                    cantidadAguaMl = amount
                )
            )
        }
    }
}
