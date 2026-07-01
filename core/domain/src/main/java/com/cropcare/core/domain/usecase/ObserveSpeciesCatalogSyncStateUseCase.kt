package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.SpeciesCatalogSyncState
import com.cropcare.core.domain.repository.SpeciesCatalogSyncObserver
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Expone el estado de sincronización del catálogo de especies.
 *
 * TODO (Desarrollador 1 — UI): Conectar en [com.cropcare.feature.plants.catalog.SpeciesCatalogScreen]
 * observando este UseCase desde [com.cropcare.feature.plants.catalog.SpeciesCatalogViewModel].
 * Cuando [SpeciesCatalogSyncState.CARGANDO_DESDE_SERVIDOR], mostrar un mensaje como
 * "Conectando con el servidor..." encima del grid/lista (Render puede tardar ~50 s en despertar).
 * Cuando [SpeciesCatalogSyncState.USANDO_CACHE_LOCAL], opcionalmente mostrar un banner discreto
 * "Sin conexión — mostrando catálogo guardado".
 */
class ObserveSpeciesCatalogSyncStateUseCase @Inject constructor(
    private val syncObserver: SpeciesCatalogSyncObserver
) {
    operator fun invoke(): StateFlow<SpeciesCatalogSyncState> = syncObserver.syncState
}
