package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.event.WateringCompletedEvent
import com.cropcare.core.domain.event.WateringEventEmitter
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

data class WateringRegistrationResult(
    val recordId: Long,
    val proximaFechaRiego: Long
)

class RegisterWateringUseCase @Inject constructor(
    private val wateringRepository: WateringRepository,
    private val plantRepository: PlantRepository,
    private val getNextWateringDateUseCase: GetNextWateringDateUseCase,
    private val wateringEventEmitter: WateringEventEmitter
) {
    suspend operator fun invoke(
        plantId: Long,
        cantidadAguaMl: Int,
        notas: String? = null,
        completadoPorNotificacion: Boolean = false
    ): WateringRegistrationResult {
        val plant = plantRepository.getPlantById(plantId).first()
            ?: throw IllegalArgumentException("Planta no encontrada: $plantId")

        val timestamp = System.currentTimeMillis()
        val record = WateringRecord(
            plantId = plantId,
            timestamp = timestamp,
            cantidadAguaMl = cantidadAguaMl,
            completadoPorNotificacion = completadoPorNotificacion,
            notas = notas
        )
        val recordId = wateringRepository.insertRecord(record)

        val proximaFechaRiego = getNextWateringDateUseCase(timestamp, plant.frecuenciaRiegoDias)

        plantRepository.updatePlant(
            plant.copy(estadoRiego = WateringStatus.AL_DIA)
        )

        wateringEventEmitter.emitWateringCompleted(
            WateringCompletedEvent(
                plantId = plantId,
                proximaFechaRiego = proximaFechaRiego,
                timestamp = timestamp
            )
        )

        return WateringRegistrationResult(
            recordId = recordId,
            proximaFechaRiego = proximaFechaRiego
        )
    }
}
