package com.cropcare.feature.notifications.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.cropcare.core.domain.repository.PlantNotificationScheduler
import com.cropcare.feature.notifications.NotificationConstants
import com.cropcare.feature.notifications.receiver.WateringAlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmManagerPlantNotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager
) : PlantNotificationScheduler {

    override fun schedule(
        plantId: Long,
        plantNombre: String,
        cantidadAguaMl: Int,
        triggerAtMillis: Long
    ) {
        if (!canScheduleExactAlarms()) return

        val intent = Intent(context, WateringAlarmReceiver::class.java).apply {
            action = NotificationConstants.ACTION_WATERING_ALARM
            putExtra(NotificationConstants.EXTRA_PLANT_ID, plantId)
            putExtra(NotificationConstants.EXTRA_PLANT_NOMBRE, plantNombre)
            putExtra(NotificationConstants.EXTRA_CANTIDAD_AGUA, cantidadAguaMl)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            plantId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = triggerAtMillis.coerceAtLeast(System.currentTimeMillis() + 1_000L)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    override fun cancel(plantId: Long) {
        val intent = Intent(context, WateringAlarmReceiver::class.java).apply {
            action = NotificationConstants.ACTION_WATERING_ALARM
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            plantId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}
