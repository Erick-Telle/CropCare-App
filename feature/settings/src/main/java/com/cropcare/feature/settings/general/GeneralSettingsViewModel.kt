package com.cropcare.feature.settings.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.usecase.GetAppUsageSummaryUseCase
import com.cropcare.feature.settings.data.SettingsPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GeneralSettingsUiState(
    val notificationsEnabled: Boolean = true,
    val reminderHour: Int = 9,
    val reminderMinute: Int = 0,
    val showTimePicker: Boolean = false,
    val totalPlants: Int? = null,
    val totalWaterings: Int? = null,
    val showUsageSummary: Boolean = false,
    val isLoadingUsage: Boolean = false
)

@HiltViewModel
class GeneralSettingsViewModel @Inject constructor(
    private val settingsPreferencesRepository: SettingsPreferencesRepository,
    private val getAppUsageSummaryUseCase: GetAppUsageSummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GeneralSettingsUiState())
    val uiState: StateFlow<GeneralSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsPreferencesRepository.getPreferences().collect { prefs ->
                _uiState.update {
                    it.copy(
                        notificationsEnabled = prefs.notificationsEnabled,
                        reminderHour = prefs.preferredReminderHour,
                        reminderMinute = prefs.preferredReminderMinute
                    )
                }
            }
        }
    }

    fun onNotificationsEnabledChange(enabled: Boolean) {
        viewModelScope.launch {
            settingsPreferencesRepository.setNotificationsEnabled(enabled)
        }
    }

    fun showTimePicker() {
        _uiState.update { it.copy(showTimePicker = true) }
    }

    fun dismissTimePicker() {
        _uiState.update { it.copy(showTimePicker = false) }
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        viewModelScope.launch {
            settingsPreferencesRepository.setPreferredReminderTime(hour, minute)
            _uiState.update { it.copy(showTimePicker = false) }
        }
    }

    fun loadUsageSummary() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingUsage = true, showUsageSummary = true) }
            val summary = getAppUsageSummaryUseCase()
            _uiState.update {
                it.copy(
                    isLoadingUsage = false,
                    totalPlants = summary.totalPlants,
                    totalWaterings = summary.totalWaterings
                )
            }
        }
    }
}
