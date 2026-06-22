package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.repository.PlantNotificationScheduler
import com.cropcare.core.domain.repository.PlantRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SchedulePlantNotificationUseCase @Inject constructor(
    private val plantNotificationScheduler: PlantNotificationScheduler,
    private val plantRepository: PlantRepository
) {
    suspend operator fun invoke(
        plantId: Long,
        plantNombre: String,
        proximaFechaRiego: Long
    ) {
        val plant = plantRepository.getPlantById(plantId).first() ?: return
        plantNotificationScheduler.schedule(
            plantId = plantId,
            plantNombre = plantNombre,
            cantidadAguaMl = plant.cantidadAguaMl,
            triggerAtMillis = proximaFechaRiego
        )
    }
}
