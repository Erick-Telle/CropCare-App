package com.cropcare.feature.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val manager = context.getSystemService(NotificationManager::class.java)

        val wateringChannel = NotificationChannel(
            NotificationConstants.CHANNEL_WATERING,
            "Recordatorios de riego",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Recordatorios para regar tus plantas"
            enableVibration(true)
        }

        val urgentChannel = NotificationChannel(
            NotificationConstants.CHANNEL_URGENT,
            "Alertas urgentes de riego",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Alertas cuando una planta lleva varios días sin riego"
            enableVibration(true)
        }

        manager.createNotificationChannel(wateringChannel)
        manager.createNotificationChannel(urgentChannel)
    }

    fun areNotificationsEnabled(context: Context): Boolean =
        NotificationManagerCompat.from(context).areNotificationsEnabled()
}
