package com.cropcare.feature.plants.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.model.Season
import com.cropcare.core.domain.usecase.CompleteOnboardingUseCase
import com.cropcare.core.domain.usecase.SaveClimateConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingClimateUiState(
    val temperatura: String = "22",
    val humedad: String = "50",
    val estacion: Season = Season.PRIMAVERA,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveClimateConfigUseCase: SaveClimateConfigUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _climateState = MutableStateFlow(OnboardingClimateUiState())
    val climateState: StateFlow<OnboardingClimateUiState> = _climateState.asStateFlow()

    fun onTemperaturaChange(value: String) {
        _climateState.update { it.copy(temperatura = value, errorMessage = null) }
    }

    fun onHumedadChange(value: String) {
        _climateState.update { it.copy(humedad = value, errorMessage = null) }
    }

    fun onEstacionChange(season: Season) {
        _climateState.update { it.copy(estacion = season) }
    }

    fun saveClimateAndComplete(onSuccess: () -> Unit) {
        val state = _climateState.value
        val temp = state.temperatura.toFloatOrNull()
        val humidity = state.humedad.toFloatOrNull()

        if (temp == null || temp !in 5f..45f) {
            _climateState.update { it.copy(errorMessage = "Ingresa una temperatura válida (5-45 °C)") }
            return
        }
        if (humidity == null || humidity !in 10f..100f) {
            _climateState.update { it.copy(errorMessage = "Ingresa una humedad válida (10-100 %)") }
            return
        }

        viewModelScope.launch {
            _climateState.update { it.copy(isSaving = true, errorMessage = null) }
            try {
                saveClimateConfigUseCase(
                    ClimateConfig(
                        temperaturaPromedio = temp,
                        humedadAmbiental = humidity,
                        estacionActual = state.estacion
                    )
                )
                completeOnboardingUseCase()
                onSuccess()
            } catch (e: Exception) {
                _climateState.update {
                    it.copy(isSaving = false, errorMessage = "Error al guardar la configuración")
                }
            }
        }
    }
}
