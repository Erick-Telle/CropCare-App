package com.cropcare.feature.settings.data

import kotlinx.coroutines.flow.Flow

data class AppPreferences(
    val notificationsEnabled: Boolean = true,
    val preferredReminderHour: Int = 9,
    val preferredReminderMinute: Int = 0
)

interface SettingsPreferencesRepository {
    fun getPreferences(): Flow<AppPreferences>
    suspend fun setNotificationsEnabled(enabled: Boolean)
    suspend fun setPreferredReminderTime(hour: Int, minute: Int)
}
