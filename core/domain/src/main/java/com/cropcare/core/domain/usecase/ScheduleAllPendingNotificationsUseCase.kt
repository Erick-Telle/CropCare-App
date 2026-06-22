package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ScheduleAllPendingNotificationsUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val wateringRepository: WateringRepository,
    private val getNextWateringDateUseCase: GetNextWateringDateUseCase,
    private val schedulePlantNotificationUseCase: SchedulePlantNotificationUseCase
) {
    suspend operator fun invoke() {
        val plants = plantRepository.getAllPlants().first().filter { it.activa }
        plants.forEach { plant ->
            val records = wateringRepository.getRecordsByPlantId(plant.id).first()
            val lastWatering = records.firstOrNull()?.timestamp ?: plant.fechaCreacion
            val proximaFechaRiego = getNextWateringDateUseCase(lastWatering, plant.frecuenciaRiegoDias)
            schedulePlantNotificationUseCase(
                plantId = plant.id,
                plantNombre = plant.nombre,
                proximaFechaRiego = proximaFechaRiego
            )
        }
    }
}
