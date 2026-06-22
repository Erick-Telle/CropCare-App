package com.cropcare.feature.notifications

import android.content.Context
import com.cropcare.feature.notifications.coordinator.NotificationCoordinator
import com.cropcare.feature.notifications.worker.DailyWateringCheckWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationCoordinator: NotificationCoordinator
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun initialize() {
        NotificationChannels.createChannels(context)
        DailyWateringCheckWorker.schedule(context)
        notificationCoordinator.start(scope)
        scope.launch {
            notificationCoordinator.scheduleAllOnStartup()
        }
    }
}
