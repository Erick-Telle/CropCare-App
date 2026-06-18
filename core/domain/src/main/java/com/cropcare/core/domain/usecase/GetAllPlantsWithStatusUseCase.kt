package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.PlantWithStatus
import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllPlantsWithStatusUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val wateringRepository: WateringRepository,
    private val getNextWateringDateUseCase: GetNextWateringDateUseCase,
    private val getPlantWateringStatusUseCase: GetPlantWateringStatusUseCase
) {
    operator fun invoke(): Flow<List<PlantWithStatus>> =
        plantRepository.getAllPlants().flatMapLatest { plants ->
            if (plants.isEmpty()) {
                flowOf(emptyList())
            } else {
                val recordFlows = plants.map { plant ->
                    wateringRepository.getRecordsByPlantId(plant.id)
                }
                combine(recordFlows) { recordsArray ->
                    plants.mapIndexed { index, plant ->
                        val ultimoRiego = recordsArray[index].firstOrNull()?.timestamp
                        buildPlantWithStatus(plant, ultimoRiego)
                    }.sortedBy { it.plant.nombre }
                }
            }
        }

    private fun buildPlantWithStatus(
        plant: com.cropcare.core.domain.model.Plant,
        ultimoRiego: Long?
    ): PlantWithStatus {
        val lastWatering = ultimoRiego ?: plant.fechaCreacion
        val proximaFechaRiego = getNextWateringDateUseCase(lastWatering, plant.frecuenciaRiegoDias)
        val diasRestantes = getPlantWateringStatusUseCase.calculateDaysRemaining(proximaFechaRiego)
        val status = getPlantWateringStatusUseCase(lastWatering, plant.frecuenciaRiegoDias)

        return PlantWithStatus(
            plant = plant.copy(estadoRiego = status),
            status = status,
            proximaFechaRiego = proximaFechaRiego,
            diasRestantes = diasRestantes
        )
    }
}
