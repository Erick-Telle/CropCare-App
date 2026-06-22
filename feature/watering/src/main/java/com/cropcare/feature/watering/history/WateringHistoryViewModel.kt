package com.cropcare.feature.watering.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase
import com.cropcare.core.domain.usecase.GetWateringHistoryUseCase
import com.cropcare.feature.watering.navigation.WateringRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WateringHistoryUiState(
    val isLoading: Boolean = true,
    val plantName: String = "",
    val records: List<WateringRecord> = emptyList(),
    val totalWaterings: Int = 0,
    val lastWateringText: String = "—",
    val averageWaterMl: Int = 0
)

@HiltViewModel
class WateringHistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWateringHistoryUseCase: GetWateringHistoryUseCase,
    private val getPlantByIdUseCase: GetPlantByIdUseCase
) : ViewModel() {

    private val plantId: Long = checkNotNull(savedStateHandle[WateringRoutes.ARG_PLANT_ID])

    private val _uiState = MutableStateFlow(WateringHistoryUiState())
    val uiState: StateFlow<WateringHistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getPlantByIdUseCase(plantId).filterNotNull(),
                getWateringHistoryUseCase(plantId)
            ) { plant, records ->
                buildUiState(plant, records)
            }.collect { state ->
                _uiState.update { state }
            }
        }
    }

    private fun buildUiState(plant: Plant, records: List<WateringRecord>): WateringHistoryUiState {
        val average = if (records.isNotEmpty()) {
            records.map { it.cantidadAguaMl }.average().toInt()
        } else {
            0
        }
        val lastText = records.firstOrNull()?.let { formatDate(it.timestamp) } ?: "—"

        return WateringHistoryUiState(
            isLoading = false,
            plantName = plant.nombre,
            records = records,
            totalWaterings = records.size,
            lastWateringText = lastText,
            averageWaterMl = average
        )
    }

    private fun formatDate(timestamp: Long): String {
        val formatter = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale("es", "ES"))
        return formatter.format(java.util.Date(timestamp))
    }
}
