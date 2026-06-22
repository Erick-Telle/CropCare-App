package com.cropcare.feature.notifications.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cropcare.feature.notifications.NotificationConstants
import com.cropcare.feature.notifications.WateringNotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WateringAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: WateringNotificationHelper

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != NotificationConstants.ACTION_WATERING_ALARM) return

        val plantId = intent.getLongExtra(NotificationConstants.EXTRA_PLANT_ID, -1L)
        val plantNombre = intent.getStringExtra(NotificationConstants.EXTRA_PLANT_NOMBRE) ?: return
        val cantidadAguaMl = intent.getIntExtra(NotificationConstants.EXTRA_CANTIDAD_AGUA, 0)

        if (plantId == -1L) return

        notificationHelper.showWateringReminder(
            plantId = plantId,
            plantNombre = plantNombre,
            cantidadAguaMl = cantidadAguaMl
        )
    }
}
