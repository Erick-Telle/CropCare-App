package com.cropcare.feature.notifications.coordinator

import com.cropcare.core.domain.event.WateringEventEmitter
import com.cropcare.core.domain.repository.ClimateRepository
import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.usecase.CancelPlantNotificationUseCase
import com.cropcare.core.domain.usecase.RescheduleAfterWateringUseCase
import com.cropcare.core.domain.usecase.ScheduleAllPendingNotificationsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationCoordinator @Inject constructor(
    private val wateringEventEmitter: WateringEventEmitter,
    private val rescheduleAfterWateringUseCase: RescheduleAfterWateringUseCase,
    private val scheduleAllPendingNotificationsUseCase: ScheduleAllPendingNotificationsUseCase,
    private val cancelPlantNotificationUseCase: CancelPlantNotificationUseCase,
    private val climateRepository: ClimateRepository,
    private val plantRepository: PlantRepository
) {
    private var previousPlantIds: Set<Long> = emptySet()

    fun start(scope: CoroutineScope) {
        scope.launch {
            wateringEventEmitter.wateringCompletedEvents.collect { event ->
                rescheduleAfterWateringUseCase(
                    plantId = event.plantId,
                    proximaFechaRiego = event.proximaFechaRiego
                )
            }
        }

        scope.launch {
            climateRepository.getClimateConfig()
                .map { it?.fechaUltimaActualizacion }
                .distinctUntilChanged()
                .drop(1)
                .collect {
                    scheduleAllPendingNotificationsUseCase()
                }
        }

        scope.launch {
            plantRepository.getAllPlants()
                .map { plants -> plants.map { it.id to it.fechaCreacion }.toSet() }
                .distinctUntilChanged()
                .drop(1)
                .collect {
                    scheduleAllPendingNotificationsUseCase()
                }
        }

        scope.launch {
            plantRepository.getAllPlants().collect { plants ->
                val currentIds = plants.map { it.id }.toSet()

                if (previousPlantIds.isNotEmpty()) {
                    (previousPlantIds - currentIds).forEach { removedId ->
                        cancelPlantNotificationUseCase(removedId)
                    }
                }

                plants.filter { !it.activa }.forEach { plant ->
                    cancelPlantNotificationUseCase(plant.id)
                }

                previousPlantIds = currentIds
            }
        }
    }

    suspend fun scheduleAllOnStartup() {
        scheduleAllPendingNotificationsUseCase()
    }
}
