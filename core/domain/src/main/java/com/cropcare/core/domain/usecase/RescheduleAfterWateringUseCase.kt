package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.repository.PlantRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RescheduleAfterWateringUseCase @Inject constructor(
    private val cancelPlantNotificationUseCase: CancelPlantNotificationUseCase,
    private val schedulePlantNotificationUseCase: SchedulePlantNotificationUseCase,
    private val plantRepository: PlantRepository
) {
    suspend operator fun invoke(plantId: Long, proximaFechaRiego: Long) {
        cancelPlantNotificationUseCase(plantId)
        val plant = plantRepository.getPlantById(plantId).first() ?: return
        schedulePlantNotificationUseCase(
            plantId = plantId,
            plantNombre = plant.nombre,
            proximaFechaRiego = proximaFechaRiego
        )
    }
}
