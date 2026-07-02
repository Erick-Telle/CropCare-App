package com.cropcare.feature.plants.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.model.SpeciesCatalogSyncState
import com.cropcare.core.domain.usecase.GetSpeciesCatalogUseCase
import com.cropcare.core.domain.usecase.ObserveSpeciesCatalogSyncStateUseCase
import com.cropcare.core.domain.usecase.SearchSpeciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SpeciesCatalogUiState(
    val searchQuery: String = "",
    val species: List<Species> = emptyList(),
    val isLoading: Boolean = true,
    val syncState: SpeciesCatalogSyncState = SpeciesCatalogSyncState.IDLE
)

@HiltViewModel
class SpeciesCatalogViewModel @Inject constructor(
    private val getSpeciesCatalogUseCase: GetSpeciesCatalogUseCase,
    private val searchSpeciesUseCase: SearchSpeciesUseCase,
    private val observeSpeciesCatalogSyncStateUseCase: ObserveSpeciesCatalogSyncStateUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _uiState = MutableStateFlow(SpeciesCatalogUiState())
    val uiState: StateFlow<SpeciesCatalogUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeSpeciesCatalogSyncStateUseCase().collect { syncState ->
                _uiState.update { it.copy(syncState = syncState) }
            }
        }

        viewModelScope.launch {
            _searchQuery.flatMapLatest { query ->
                if (query.isBlank()) {
                    getSpeciesCatalogUseCase()
                } else {
                    searchSpeciesUseCase(query)
                }
            }.collect { species ->
                _uiState.update {
                    it.copy(
                        searchQuery = _searchQuery.value,
                        species = species,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query) }
    }
}
