package com.cropcare.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.cropcare.core.domain.repository.OnboardingRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "onboarding_preferences"
)

@Singleton
class OnboardingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : OnboardingRepository {

    private val onboardingCompletedKey = booleanPreferencesKey("onboarding_completed")

    override fun isOnboardingCompleted(): Flow<Boolean> =
        context.onboardingDataStore.data.map { prefs ->
            prefs[onboardingCompletedKey] ?: false
        }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        context.onboardingDataStore.edit { prefs ->
            prefs[onboardingCompletedKey] = completed
        }
    }
}
