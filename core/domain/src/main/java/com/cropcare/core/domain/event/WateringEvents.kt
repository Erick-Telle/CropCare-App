package com.cropcare.core.domain.event

import kotlinx.coroutines.flow.SharedFlow

data class WateringCompletedEvent(
    val plantId: Long,
    val proximaFechaRiego: Long,
    val timestamp: Long
)

interface WateringEventEmitter {
    val wateringCompletedEvents: SharedFlow<WateringCompletedEvent>
    suspend fun emitWateringCompleted(event: WateringCompletedEvent)
}
