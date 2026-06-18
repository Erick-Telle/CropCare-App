package com.cropcare.feature.settings.climate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.model.Season
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.usecase.CalculateWateringFrequencyUseCase
import com.cropcare.core.domain.usecase.GetClimateConfigUseCase
import com.cropcare.core.domain.usecase.GetSpeciesCatalogUseCase
import com.cropcare.core.domain.usecase.RecalculateAllPlantsWateringUseCase
import com.cropcare.core.domain.usecase.SaveClimateConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClimateConfigUiState(
    val isLoading: Boolean = true,
    val temperature: Float = 22f,
    val humidity: Float = 50f,
    val season: Season = Season.PRIMAVERA,
    val previewText: String = "",
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class ClimateConfigViewModel @Inject constructor(
    private val getClimateConfigUseCase: GetClimateConfigUseCase,
    private val saveClimateConfigUseCase: SaveClimateConfigUseCase,
    private val recalculateAllPlantsWateringUseCase: RecalculateAllPlantsWateringUseCase,
    private val calculateWateringFrequencyUseCase: CalculateWateringFrequencyUseCase,
    private val getSpeciesCatalogUseCase: GetSpeciesCatalogUseCase
) : ViewModel() {

    private var previewSpecies: Species? = null

    private val _uiState = MutableStateFlow(ClimateConfigUiState())
    val uiState: StateFlow<ClimateConfigUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getClimateConfigUseCase(),
                getSpeciesCatalogUseCase()
            ) { config, speciesList ->
                previewSpecies = speciesList.find {
                    it.nombreComun.contains("Cactus", ignoreCase = true)
                } ?: speciesList.firstOrNull()

                val temperature = config?.temperaturaPromedio ?: 22f
                val humidity = config?.humedadAmbiental ?: 50f
                val season = config?.estacionActual ?: Season.PRIMAVERA

                ClimateConfigUiState(
                    isLoading = false,
                    temperature = temperature,
                    humidity = humidity,
                    season = season,
                    previewText = buildPreviewText(temperature, humidity, season)
                )
            }.collect { state ->
                _uiState.update { current ->
                    state.copy(
                        isSaving = current.isSaving,
                        saveSuccess = current.saveSuccess
                    )
                }
            }
        }
    }

    fun onTemperatureChange(value: Float) {
        _uiState.update {
            it.copy(
                temperature = value,
                previewText = buildPreviewText(value, it.humidity, it.season),
                saveSuccess = false
            )
        }
    }

    fun onHumidityChange(value: Float) {
        _uiState.update {
            it.copy(
                humidity = value,
                previewText = buildPreviewText(it.temperature, value, it.season),
                saveSuccess = false
            )
        }
    }

    fun onSeasonChange(season: Season) {
        _uiState.update {
            it.copy(
                season = season,
                previewText = buildPreviewText(it.temperature, it.humidity, season),
                saveSuccess = false
            )
        }
    }

    fun saveConfiguration() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            val config = ClimateConfig(
                temperaturaPromedio = state.temperature,
                humedadAmbiental = state.humidity,
                estacionActual = state.season,
                fechaUltimaActualizacion = System.currentTimeMillis()
            )
            saveClimateConfigUseCase(config)
            recalculateAllPlantsWateringUseCase(config)

            _uiState.update {
                it.copy(isSaving = false, saveSuccess = true)
            }
        }
    }

    private fun buildPreviewText(temperature: Float, humidity: Float, season: Season): String {
        val species = previewSpecies ?: return "Configura el clima para ajustar los recordatorios de riego."
        val climate = ClimateConfig(
            temperaturaPromedio = temperature,
            humedadAmbiental = humidity,
            estacionActual = season
        )
        val frequency = calculateWateringFrequencyUseCase(species, climate)
        return "Con estos valores, un ${species.nombreComun.lowercase()} se regaría cada $frequency días."
    }
}
