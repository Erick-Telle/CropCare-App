package com.cropcare.feature.settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "app_settings"
)

@Singleton
class SettingsPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsPreferencesRepository {

    private val notificationsEnabledKey = booleanPreferencesKey("notifications_enabled")
    private val reminderHourKey = intPreferencesKey("reminder_hour")
    private val reminderMinuteKey = intPreferencesKey("reminder_minute")

    override fun getPreferences(): Flow<AppPreferences> =
        context.settingsDataStore.data.map { prefs ->
            AppPreferences(
                notificationsEnabled = prefs[notificationsEnabledKey] ?: true,
                preferredReminderHour = prefs[reminderHourKey] ?: 9,
                preferredReminderMinute = prefs[reminderMinuteKey] ?: 0
            )
        }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[notificationsEnabledKey] = enabled
        }
    }

    override suspend fun setPreferredReminderTime(hour: Int, minute: Int) {
        context.settingsDataStore.edit { prefs ->
            prefs[reminderHourKey] = hour
            prefs[reminderMinuteKey] = minute
        }
    }
}
