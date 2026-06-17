package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsOnboardingCompletedUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(): Flow<Boolean> = onboardingRepository.isOnboardingCompleted()
}
