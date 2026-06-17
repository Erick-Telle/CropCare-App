package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.repository.PlantRepository
import javax.inject.Inject

class SavePlantUseCase @Inject constructor(
    private val plantRepository: PlantRepository
) {
    suspend operator fun invoke(plant: Plant): Long {
        return if (plant.id == 0L) {
            plantRepository.insertPlant(plant)
        } else {
            plantRepository.updatePlant(plant)
            plant.id
        }
    }
}
