package com.cropcare.feature.notifications.coordinator

import com.cropcare.core.domain.event.WateringEventEmitter
import com.cropcare.core.domain.repository.ClimateRepository
import com.cropcare.core.domain.usecase.RescheduleAfterWateringUseCase
import com.cropcare.core.domain.usecase.ScheduleAllPendingNotificationsUseCase
import com.cropcare.core.domain.repository.PlantRepository
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
    private val climateRepository: ClimateRepository,
    private val plantRepository: PlantRepository
) {

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
    }

    suspend fun scheduleAllOnStartup() {
        scheduleAllPendingNotificationsUseCase()
    }
}
