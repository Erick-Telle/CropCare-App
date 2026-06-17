package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.repository.OnboardingRepository
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke() {
        onboardingRepository.setOnboardingCompleted(true)
    }
}
