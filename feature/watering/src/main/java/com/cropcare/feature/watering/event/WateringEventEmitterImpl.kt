package com.cropcare.feature.watering.event

import com.cropcare.core.domain.event.WateringCompletedEvent
import com.cropcare.core.domain.event.WateringEventEmitter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WateringEventEmitterImpl @Inject constructor() : WateringEventEmitter {

    private val _events = MutableSharedFlow<WateringCompletedEvent>(extraBufferCapacity = 1)
    override val wateringCompletedEvents: SharedFlow<WateringCompletedEvent> = _events.asSharedFlow()

    override suspend fun emitWateringCompleted(event: WateringCompletedEvent) {
        _events.emit(event)
    }
}
