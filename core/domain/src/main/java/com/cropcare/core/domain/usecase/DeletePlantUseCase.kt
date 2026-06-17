package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.repository.PlantRepository
import javax.inject.Inject

class DeletePlantUseCase @Inject constructor(
    private val plantRepository: PlantRepository
) {
    suspend operator fun invoke(plant: Plant) {
        plantRepository.deletePlant(plant)
    }
}
