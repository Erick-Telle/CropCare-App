package com.cropcare.feature.notifications.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cropcare.core.domain.repository.PlantNotificationScheduler
import com.cropcare.feature.notifications.NotificationConstants
import com.cropcare.feature.notifications.WateringNotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SnoozeNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var plantNotificationScheduler: PlantNotificationScheduler

    @Inject
    lateinit var notificationHelper: WateringNotificationHelper

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != NotificationConstants.ACTION_SNOOZE) return

        val plantId = intent.getLongExtra(NotificationConstants.EXTRA_PLANT_ID, -1L)
        val plantNombre = intent.getStringExtra(NotificationConstants.EXTRA_PLANT_NOMBRE) ?: return
        val cantidadAguaMl = intent.getIntExtra(NotificationConstants.EXTRA_CANTIDAD_AGUA, 0)
        val notificationId = intent.getIntExtra(NotificationConstants.EXTRA_NOTIFICATION_ID, -1)

        if (plantId == -1L) return

        val snoozeTime = System.currentTimeMillis() + NotificationConstants.SNOOZE_MILLIS

        plantNotificationScheduler.schedule(
            plantId = plantId,
            plantNombre = plantNombre,
            cantidadAguaMl = cantidadAguaMl,
            triggerAtMillis = snoozeTime
        )

        if (notificationId != -1) {
            notificationHelper.dismissNotification(notificationId)
        }
    }
}
