package com.cropcare.feature.catalog.care

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.usecase.CareAdvice
import com.cropcare.core.domain.usecase.GetCareAdviceUseCase
import com.cropcare.feature.catalog.navigation.CatalogRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CareAdviceUiState(
    val isLoading: Boolean = true,
    val careAdvice: CareAdvice? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class CareAdviceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCareAdviceUseCase: GetCareAdviceUseCase
) : ViewModel() {

    private val especieId: Long = checkNotNull(savedStateHandle[CatalogRoutes.ARG_ESPECIE_ID])

    private val _uiState = MutableStateFlow(CareAdviceUiState())
    val uiState: StateFlow<CareAdviceUiState> = _uiState.asStateFlow()

    init {
        loadCareAdvice()
    }

    private fun loadCareAdvice() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val advice = getCareAdviceUseCase(especieId)
            _uiState.update {
                if (advice != null) {
                    it.copy(isLoading = false, careAdvice = advice)
                } else {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se encontró la especie solicitada"
                    )
                }
            }
        }
    }
}
