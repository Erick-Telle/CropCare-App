package com.cropcare.core.domain.model

/**
 * Estado de sincronización del catálogo de especies con el servidor remoto.
 */
enum class SpeciesCatalogSyncState {
    /** Sin sincronización activa. */
    IDLE,

    /** Petición en curso al servidor (puede tardar hasta ~50 s si Render está dormido). */
    CARGANDO_DESDE_SERVIDOR,

    /** Falló la red; se muestran datos cacheados en Room. */
    USANDO_CACHE_LOCAL,

    /** Catálogo actualizado desde el servidor y guardado en caché local. */
    ACTUALIZADO
}
