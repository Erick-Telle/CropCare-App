package com.cropcare.feature.notifications

object NotificationConstants {
    const val CHANNEL_WATERING = "watering_reminders"
    const val CHANNEL_URGENT = "watering_urgent"

    const val ACTION_WATERING_ALARM = "com.cropcare.WATERING_ALARM"
    const val ACTION_WATER_NOW = "com.cropcare.WATER_NOW"
    const val ACTION_SNOOZE = "com.cropcare.SNOOZE_WATERING"

    const val EXTRA_PLANT_ID = "extra_plant_id"
    const val EXTRA_PLANT_NOMBRE = "extra_plant_nombre"
    const val EXTRA_CANTIDAD_AGUA = "extra_cantidad_agua"
    const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
    const val EXTRA_DIAS_ATRASADOS = "extra_dias_atrasados"

    const val SNOOZE_HOURS = 2
    const val SNOOZE_MILLIS = SNOOZE_HOURS * 60 * 60 * 1000L

    fun notificationIdForPlant(plantId: Long): Int = plantId.toInt()
    fun urgentNotificationIdForPlant(plantId: Long): Int = (plantId + 100_000).toInt()
}
