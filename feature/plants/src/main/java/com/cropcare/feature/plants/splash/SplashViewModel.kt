package com.cropcare.feature.plants.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.usecase.IsOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashUiState(
    val isLoading: Boolean = true,
    val onboardingCompleted: Boolean = false
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val completed = isOnboardingCompletedUseCase().first()
            _uiState.value = SplashUiState(
                isLoading = false,
                onboardingCompleted = completed
            )
        }
    }
}
