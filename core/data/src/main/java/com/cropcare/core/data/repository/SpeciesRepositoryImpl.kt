package com.cropcare.core.data.repository

import com.cropcare.core.data.local.dao.SpeciesDao
import com.cropcare.core.data.mapper.toDomain
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeciesRepositoryImpl @Inject constructor(
    private val speciesDao: SpeciesDao
) : SpeciesRepository {

    override fun getAllSpecies(): Flow<List<Species>> =
        speciesDao.getAllSpecies().map { list -> list.map { it.toDomain() } }

    override fun getSpeciesById(id: Long): Flow<Species?> =
        speciesDao.getSpeciesById(id).map { it?.toDomain() }

    override fun searchSpeciesByName(query: String): Flow<List<Species>> =
        speciesDao.searchSpeciesByName(query).map { list -> list.map { it.toDomain() } }
}
