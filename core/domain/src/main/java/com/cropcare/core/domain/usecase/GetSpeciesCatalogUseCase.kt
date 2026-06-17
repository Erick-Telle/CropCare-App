package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpeciesCatalogUseCase @Inject constructor(
    private val speciesRepository: SpeciesRepository
) {
    operator fun invoke(): Flow<List<Species>> = speciesRepository.getAllSpecies()
}
