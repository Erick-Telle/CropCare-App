package com.cropcare.core.domain.repository

import com.cropcare.core.domain.model.SpeciesCatalogSyncState
import kotlinx.coroutines.flow.StateFlow

/**
 * Observador aditivo del estado de sincronización del catálogo.
 * No forma parte de [SpeciesRepository] para no alterar su contrato existente.
 */
interface SpeciesCatalogSyncObserver {
    val syncState: StateFlow<SpeciesCatalogSyncState>
}
