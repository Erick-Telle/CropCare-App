package com.cropcare.feature.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.cropcare.feature.notifications.receiver.SnoozeNotificationReceiver
import com.cropcare.feature.notifications.receiver.WaterNowReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WateringNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {

    fun showWateringReminder(
        plantId: Long,
        plantNombre: String,
        cantidadAguaMl: Int
    ) {
        val notificationId = NotificationConstants.notificationIdForPlant(plantId)

        val contentIntent = createPlantDetailIntent(plantId)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val waterNowIntent = Intent(context, WaterNowReceiver::class.java).apply {
            action = NotificationConstants.ACTION_WATER_NOW
            putExtra(NotificationConstants.EXTRA_PLANT_ID, plantId)
            putExtra(NotificationConstants.EXTRA_CANTIDAD_AGUA, cantidadAguaMl)
            putExtra(NotificationConstants.EXTRA_NOTIFICATION_ID, notificationId)
        }
        val waterNowPendingIntent = PendingIntent.getBroadcast(
            context,
            (plantId * 10 + 1).toInt(),
            waterNowIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozeIntent = Intent(context, SnoozeNotificationReceiver::class.java).apply {
            action = NotificationConstants.ACTION_SNOOZE
            putExtra(NotificationConstants.EXTRA_PLANT_ID, plantId)
            putExtra(NotificationConstants.EXTRA_PLANT_NOMBRE, plantNombre)
            putExtra(NotificationConstants.EXTRA_CANTIDAD_AGUA, cantidadAguaMl)
            putExtra(NotificationConstants.EXTRA_NOTIFICATION_ID, notificationId)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            (plantId * 10 + 2).toInt(),
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_WATERING)
            .setSmallIcon(R.drawable.ic_water_drop)
            .setContentTitle("🌿 Hora de regar a $plantNombre")
            .setContentText("Tu planta necesita $cantidadAguaMl ml de agua hoy")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setColor(0xFF2E7D32.toInt())
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .addAction(
                R.drawable.ic_water_drop,
                "Regar ahora",
                waterNowPendingIntent
            )
            .addAction(
                R.drawable.ic_water_drop,
                "Posponer 2 horas",
                snoozePendingIntent
            )
            .build()

        notificationManager.notify(notificationId, notification)
    }

    fun showUrgentReminder(
        plantId: Long,
        plantNombre: String,
        cantidadAguaMl: Int,
        diasAtrasados: Int
    ) {
        val notificationId = NotificationConstants.urgentNotificationIdForPlant(plantId)

        val contentIntent = createPlantDetailIntent(plantId)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_URGENT)
            .setSmallIcon(R.drawable.ic_water_drop)
            .setContentTitle("⚠️ $plantNombre lleva $diasAtrasados días sin riego")
            .setContentText("Riega con $cantidadAguaMl ml lo antes posible")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setColor(0xFFE53935.toInt())
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$plantNombre necesita atención urgente. Lleva $diasAtrasados días sin riego. Cantidad sugerida: $cantidadAguaMl ml.")
            )
            .build()

        notificationManager.notify(notificationId, notification)
    }

    fun dismissNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    private fun createPlantDetailIntent(plantId: Long): Intent {
        return context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(NotificationConstants.EXTRA_PLANT_ID, plantId)
        } ?: Intent().apply {
            setPackage(context.packageName)
            putExtra(NotificationConstants.EXTRA_PLANT_ID, plantId)
        }
    }
}
