package com.cropcare.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setOnboardingCompleted(completed: Boolean)
}
