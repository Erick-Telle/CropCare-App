package com.cropcare.core.data.repository

import android.util.Log
import com.cropcare.core.data.di.ApplicationScope
import com.cropcare.core.data.local.dao.SpeciesDao
import com.cropcare.core.data.mapper.toDomain
import com.cropcare.core.data.remote.api.SpeciesApiService
import com.cropcare.core.data.remote.mapper.toEntity
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.model.SpeciesCatalogSyncState
import com.cropcare.core.domain.repository.SpeciesCatalogSyncObserver
import com.cropcare.core.domain.repository.SpeciesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesRepositoryImpl @Inject constructor(
    private val speciesDao: SpeciesDao,
    private val speciesApi: SpeciesApiService,
    @ApplicationScope private val applicationScope: CoroutineScope
) : SpeciesRepository, SpeciesCatalogSyncObserver {

    private val _syncState = MutableStateFlow(SpeciesCatalogSyncState.IDLE)
    override val syncState: StateFlow<SpeciesCatalogSyncState> = _syncState.asStateFlow()

    private val syncMutex = Mutex()

    override fun getAllSpecies(): Flow<List<Species>> =
        speciesDao.getAllSpecies()
            .map { entities -> entities.map { it.toDomain() } }
            .onStart { scheduleRemoteSync { syncAllSpeciesFromRemote() } }

    override fun getSpeciesById(id: Long): Flow<Species?> =
        speciesDao.getSpeciesById(id)
            .map { entity -> entity?.toDomain() }
            .onStart { scheduleRemoteSync { syncSpeciesByIdFromRemote(id) } }

    override fun searchSpeciesByName(query: String): Flow<List<Species>> =
        speciesDao.searchSpeciesByName(query)
            .map { entities -> entities.map { it.toDomain() } }
            .onStart {
                scheduleRemoteSync {
                    if (query.isBlank()) {
                        syncAllSpeciesFromRemote()
                    } else {
                        syncSearchFromRemote(query)
                    }
                }
            }

    private fun scheduleRemoteSync(block: suspend () -> Unit) {
        applicationScope.launch {
            syncMutex.withLock {
                block()
            }
        }
    }

    private suspend fun syncAllSpeciesFromRemote() {
        _syncState.value = SpeciesCatalogSyncState.CARGANDO_DESDE_SERVIDOR
        try {
            val remoteSpecies = speciesApi.getAllSpecies()
            cacheRemoteSpecies(remoteSpecies.map { it.toEntity() })
            _syncState.value = SpeciesCatalogSyncState.ACTUALIZADO
        } catch (exception: Exception) {
            handleRemoteFailure(exception, "getAllSpecies")
        }
    }

    private suspend fun syncSpeciesByIdFromRemote(id: Long) {
        _syncState.value = SpeciesCatalogSyncState.CARGANDO_DESDE_SERVIDOR
        try {
            val remoteSpecies = speciesApi.getSpeciesById(id)
            speciesDao.insertSpecies(remoteSpecies.toEntity())
            _syncState.value = SpeciesCatalogSyncState.ACTUALIZADO
        } catch (exception: Exception) {
            handleRemoteFailure(exception, "getSpeciesById($id)")
        }
    }

    private suspend fun syncSearchFromRemote(query: String) {
        _syncState.value = SpeciesCatalogSyncState.CARGANDO_DESDE_SERVIDOR
        try {
            val remoteSpecies = speciesApi.searchSpecies(query)
            cacheRemoteSpecies(remoteSpecies.map { it.toEntity() })
            _syncState.value = SpeciesCatalogSyncState.ACTUALIZADO
        } catch (exception: Exception) {
            handleRemoteFailure(exception, "searchSpecies($query)")
        }
    }

    private suspend fun cacheRemoteSpecies(entities: List<com.cropcare.core.data.local.entity.SpeciesEntity>) {
        if (entities.isNotEmpty()) {
            speciesDao.insertAllSpecies(entities)
        }
    }

    private suspend fun handleRemoteFailure(exception: Exception, operation: String) {
        when (exception) {
            is SocketTimeoutException,
            is IOException,
            is HttpException -> Log.w(
                TAG,
                "Error de red en $operation — usando caché local de Room",
                exception
            )
            else -> Log.w(
                TAG,
                "Error inesperado en $operation — usando caché local de Room",
                exception
            )
        }
        _syncState.value = SpeciesCatalogSyncState.USANDO_CACHE_LOCAL
    }

    private companion object {
        const val TAG = "SpeciesRepository"
    }
}
